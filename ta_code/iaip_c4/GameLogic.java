import java.util.List;
import java.util.ArrayList;

public class GameLogic implements IGameLogic {
  private int x = 0;
  private int y = 0;
  private int playerID;
  private int[][] board;
  private int count = 0;

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

  private Winner getRealWinner(int id) {
    if (id == 1)
      return Winner.PLAYER1;
    else
      return Winner.PLAYER2;
  }

  private Winner gameStatus(int[][] board) {
    //TODO Write your implementation for this method
    //columns
    for(int c = 0; c < x; c++){
      for(int r = 0; r < y; r++){
        int player = board[c][r];
        if(player == 0){
          break;
        }
        //check vertical
        if(r+2<y){
          for(int a = 0; a<4; a++){
            if(player != board[c][r+a]){
              break;
            }
            else if(a == 3){
              return getRealWinner(player);
            }
          }
        }
        //check horizontal
        if(c+2<x){
          for(int a = 0; a<4; a++){
            if(player != board[c+a][r]){
              break;
            }
            else if(a == 3){
              return getRealWinner(player);
            }
          }
        }
        //check diagonalup
        if(r+2<y && c+2<x){
          for(int a = 0; 0<4; a++){
            if(player != board[c+a][r+a]){
              break;
            }
            else if(a == 3){
              return getRealWinner(player);
            }
          }
        }
        //check diagonaldown
        if(r-2>0 && c+2<x){
          for(int a = 0; 0<4; a++){
            if(player != board[c+a][r-a]){
              break;
            }
            else if(a == 3){
              return getRealWinner(player);
            }
          }
        }
      }
    }
    for (int column = 0; column < x; column++) {
      if (board[column][y-1] == 0)
        return Winner.NOT_FINISHED;
    }

    return Winner.TIE;
  }

  public Winner gameFinished() {
    //TODO Write your implementation for this method
    return gameStatus(board);
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

  private int[][] copyOf(int[][] state) {
    int[][] copy = new int[state.length][state[0].length];
    for (int column = 0; column < x; column++)
      for (int row = 0; row < y; row++)
        copy[column][row] = state[column][row];
    return copy;
  }

  public int decideNextMove() {
    //TODO Write your implementation for this method
    return count++%x;
  }

  private List<Integer> actions(int[][] state) {
    List<Integer> actions = new ArrayList<>();
    for (int i = 0; i < x; i++) {
      if (board[i][y-1] == 0)
        actions.add(i);
    }
    return actions;
  }


  private void minMix() {
  }

  private double max(int[][] state) {
    if (terminal(state))
      return utility(state);
    else {
      double best = Double.MIN_VALUE;
      for (int action : actions(state)) {
        double res = min(act(copyOf(state), action, playerID));
        if (res > best)
          best = res;
      }
      return best;
    }
  }

  private double min(int[][] state) {
    if (terminal(state))
      return utility(state);
    else {
      double best = Double.MAX_VALUE;
      for (int action : actions(state)) {
        double res = max(act(copyOf(state), action, playerID == 1 ? 2 : 1));
        if (res < best)
          best = res;
      }
      return best;
    }
  }

  private boolean terminal(int[][] state) {
    return gameStatus(state) != Winner.NOT_FINISHED;
  }

  private double utility(int[][] state) {
    return 0.0;
  }

}
