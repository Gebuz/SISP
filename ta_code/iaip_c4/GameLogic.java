import java.util.List;
import java.util.ArrayList;

public class GameLogic implements IGameLogic {
  private int x = 0;
  private int y = 0;
  private int playerID;
  private int[][] board;
  private int count = 0;
  private int cutoff = 9;

  public GameLogic() {
    //TODO Write your implementation for this method
  }

  public void initializeGame(int x, int y, int playerID) {
    this.x = x;
    this.y = y;
    this.playerID = playerID;
    this.board = new int[x][y];
    //TODO Write your implementation for this method
  }

  public Winner gameFinished() {
    //TODO Write your implementation for this method
    return BasicLogic.gameStatus(board);
  }

  public void insertCoin(int column, int playerID) {
    //TODO Write your implementation for this method	
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
    //TODO Write your implementation for this method
    //return count++%x;
    return minMax(board);
  }

  private List<Integer> actions(int[][] state) {
    List<Integer> actions = new ArrayList<>();
    for (int i = 0; i < x; i++) {
      if (board[i][y-1] == 0)
        actions.add(i);
    }
    if (actions.size() == 0)
      System.out.println("No action!");
    return actions;
  }


  private int minMax(int[][] state) {
    int bestAction = -1;
    double bestValue = -Double.MAX_VALUE;
    for (int action : actions(state)) {
      double res = min(act(BasicLogic.copyOf(state), action, playerID), 1);
      System.out.println(bestValue);
      if (res > bestValue) {
        bestValue = res;
        bestAction = action;
      }
    }
    System.out.println("My move = " + bestAction);
    return bestAction;
  }

  private double max(int[][] state, int depth) {
    if (terminal(state) || depth == cutoff)
      return utility(state);
    else {
      double best = -Double.MAX_VALUE;
      for (int action : actions(state)) {
        double res = min(act(BasicLogic.copyOf(state), action, playerID), depth+1);
        if (res > best)
          best = res;
      }
      return best;
    }
  }

  private double min(int[][] state, int depth) {
    if (terminal(state) || depth == cutoff)
      return utility(state);
    else {
      double best = Double.MAX_VALUE;
      for (int action : actions(state)) {
        double res = max(act(BasicLogic.copyOf(state), action, playerID == 1 ? 2 : 1), depth+1);
        if (res < best)
          best = res;
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
        return 1000.0;
      else if (winner == Winner.TIE)
        return 0.0;
      else
        return -1000.0;
    }
    return Heuristic.GetHeuristic(state, playerID);
  }

}
