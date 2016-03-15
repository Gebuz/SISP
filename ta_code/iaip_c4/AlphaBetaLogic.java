import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class AlphaBetaLogic implements IGameLogic {
  private int x = 0;
  private int y = 0;
  private int playerID;
  private int[][] board;
  private int cutoff = 8;
  private int maxCutoff = 30;
  private int[] bestMoves;
  private boolean firstMove = true;
  /*
   * This is kinda a hack. Since our runners only check if they are
   * interrupted after doing a full depth search, we might not die
   * before the next computation is needed. By having enough threads
   * in the pool, we should avoid this issue
  */
  private ExecutorService executor = Executors.newFixedThreadPool(3);
  private volatile int bestMove = -1;
  private int timeLimitInMs = 500;

  public AlphaBetaLogic() {
  }

  public void initializeGame(int x, int y, int playerID) {
    this.x = x;
    this.y = y;
    this.playerID = playerID;
    this.board = new int[x][y];
    bestMoves = new int[maxCutoff + 1];
    clearBestMoves();
  }

  public void initializeGame(int[][] board, int playerID) {
    this.board = board;
    x = board.length;
    y = board[0].length;
    this.playerID = playerID;
  }

  public Winner gameFinished() {
    return BasicLogic.gameStatus(board);
  }

  public void insertCoin(int column, int playerID) {
    act(board, column, playerID);
  }

  private int[][] act(int[][] state, int action, int playerID) {
    int[] myColumn = state[action];
    for (int i = 0; i < y; i++) {
      if (myColumn[i] == 0) {
        myColumn[i] = playerID;
        break;
      }
    }
    return state;
  }

  public int decideNextMove() {
    if (firstMove) {
      for (int column = 0; column < board.length; column++) {
        if (board[column][0] != 0) {
          firstMove = false;
          return iterativeDeepening();
        }
      }
      return 3;
    } else {
      return iterativeDeepening();
    }
  }

  private int iterativeDeepening() {
    bestMove = -1;
    Future<Integer> runner = executor.submit(new Callable<Integer>() {
      public Integer call() {
        for (int depth = cutoff; depth < maxCutoff; depth++) {
          if (!Thread.currentThread().isInterrupted())  {
            clearBestMoves();
            System.err.println("Running depth " + depth);
            bestMove = minMax(board, depth);
          } else {
            return -1;
          }
        }
        return -1;
      }
    });
    try {
      runner.get(timeLimitInMs, TimeUnit.MILLISECONDS);
      return bestMove;
    } catch (Exception e) {
      runner.cancel(true);
      return bestMove;
    }
  }

  private List<Integer> actions(int[][] state, int depth) {
    List<Integer> actions = new ArrayList<>();
    if (bestMoves[depth] > -1 && board[bestMoves[depth]][y-1] == 0)
      actions.add(bestMoves[depth]);
    for (int i = 0; i < x; i++) {
      if (board[i][y-1] == 0 && i != bestMoves[depth])
        actions.add(i);
    }
    if (actions.size() == 0)
      System.err.println("No action!");
    return actions;
  }

  private void clearBestMoves() {
    for (int i = 0; i < bestMoves.length; i++)
      bestMoves[i] = -1;
  }

  private int minMax(int[][] state) {
    return minMax(board, cutoff);
  }

  private int minMax(int[][] state, int depth) {
    int bestAction = -1;
    double bestValue = -Double.MAX_VALUE;
    for (int action : actions(state, depth)) {
      double res = min(act(BasicLogic.copyOf(state), action, playerID), -Double.MAX_VALUE, Double.MAX_VALUE, 1, depth);
      //System.err.println(res);
      if (res > bestValue) {
        bestValue = res;
        bestAction = action;
      }
    }
    return bestAction;
  }

  private double max(int[][] state, double alpha, double beta, int depth, int cutoff) {
    if (terminal(state) || depth == cutoff)
      return utility(state);
    else {
      double best = -Double.MAX_VALUE;
      for (int action : actions(state, depth)) {
        double res = min(act(BasicLogic.copyOf(state), action, playerID), alpha, beta, depth+1, cutoff);
        if (res >= beta) {
          bestMoves[depth] = action;
          return res;
        }
        if (res > best) {
          best = res;
          bestMoves[depth] = action;
        }
        if (res > alpha)
          alpha = res;
      }
      return best;
    }
  }

  private double min(int[][] state, double alpha, double beta, int depth, int cutoff) {
    if (terminal(state) || depth == cutoff)
      return utility(state);
    else {
      double best = Double.MAX_VALUE;
      for (int action : actions(state, depth)) {
        double res = max(act(BasicLogic.copyOf(state), action, playerID == 1 ? 2 : 1), alpha, beta, depth+1, cutoff);
        if (res <= alpha) {
          bestMoves[depth] = action;
          return res;
        }
        if (res < best) {
          best = res;
          bestMoves[depth] = action;
        }
        if (res < beta)
          beta = res;
      }
      return best;
    }
  }

  private boolean terminal(int[][] state) {
    return BasicLogic.gameStatus(state) != Winner.NOT_FINISHED;
  }

  private double utility(int[][] state) {
    if (terminal(state)) {
      Winner winner = BasicLogic.gameStatus(state);
      Winner me = BasicLogic.getRealWinner(playerID);
      if (winner == me)
        return 10000.0;
      else if (winner == Winner.TIE)
        return 0.0;
      else
        return -10000.0;
    }
    return Heuristic.GetHeuristic(state, playerID);
  }

}
