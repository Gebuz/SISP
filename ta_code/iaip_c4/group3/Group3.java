import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Group3 implements IGameLogic {
  private int x = 0;
  private int y = 0;
  private int playerID;
  private int[][] board;
  //the starting depth
  private int cutoff = 8;
  //the maximum depth to search
  private int maxCutoff = 30;
  private int[] bestMoves;
  private boolean firstMove = true;
  private ExecutorService executor = Executors.newFixedThreadPool(1);
  private volatile int bestMove = -1;
  //time limit in milliseconds, used to time out the Future
  private int timeLimitInMs = 10000;

  public Group3() {}

  //constructor for easily creating a copy of an Group3 object
  public Group3(Group3 other) {
    this.x = other.x;
    this.y = other.y;
    this.playerID = other.playerID;
    this.board = BasicLogic.copyOf(other.board);
    assert(board != other.board);
    this.cutoff = other.cutoff;
    this.maxCutoff = other.maxCutoff;
    this.bestMoves = new int[other.bestMoves.length];
    for (int i = 0; i < this.bestMoves.length; i++)
      this.bestMoves[i] = -1;
    this.firstMove = other.firstMove;
    this.bestMove = -1;
    this.timeLimitInMs = other.timeLimitInMs;
  }

  @Override
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

  @Override
  public Winner gameFinished() {
    return BasicLogic.gameStatus(board);
  }

  @Override
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

  /*
   * Creates a copy of Group3 object and runs iterativeDeepening on it
   * If this is the first move, then just choose the center column, since it is the
   * best
  */
  @Override
  public int decideNextMove() {
    Group3 copy = new Group3(this);
    if (copy.firstMove) {
      for (int column = 0; column < copy.board.length; column++) {
        if (copy.board[column][0] != 0) {
          copy.firstMove = false;
          return copy.iterativeDeepening();
        }
      }
      return 3;
    } else {
      return copy.iterativeDeepening();
    }
  }
  /*
   * Will create a Future, which in turn will run minMax on increasing depths
   * After each runthrough, the result is saved, so it can be used, in case the next
   * iteration does not complete before the timeout
   * The Future is cancelled either way and the last computed result returned
  */
  private int iterativeDeepening() {
    bestMove = -1;
    Future<Integer> runner = executor.submit(new Callable<Integer>() {
      public Integer call() {
        for (int depth = cutoff; depth < maxCutoff; depth++) {
          clearBestMoves();
          System.err.println("Running depth " + depth);
          int res = minMax(board, depth);
          if (!Thread.currentThread().isInterrupted())  {
            setBestMove(res);
          } else {
            return -1;
          }
        }
        return -1;
      }
    });
    try {
      runner.get(timeLimitInMs, TimeUnit.MILLISECONDS);
      runner.cancel(true);
      return getBestMove();
    } catch (Exception e) {
      runner.cancel(true);
      return getBestMove();
    }
  }

  private synchronized int getBestMove() {
    return bestMove;
  }
  private synchronized void setBestMove(int move) {
    this.bestMove = move;
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

  private int minMax(int[][] state, int depth) {
    int bestAction = -1;
    double bestValue = -Double.MAX_VALUE;
    for (int action : actions(state, depth)) {
      double res = min(act(BasicLogic.copyOf(state), action, playerID), 
          -Double.MAX_VALUE, Double.MAX_VALUE, 1, depth);
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
        double res = min(act(BasicLogic.copyOf(state), action, playerID), 
            alpha, beta, depth+1, cutoff);
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
        double res = max(act(BasicLogic.copyOf(state), action, playerID == 1 ? 2 : 1), 
            alpha, beta, depth+1, cutoff);
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