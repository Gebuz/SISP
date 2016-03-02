
public class GameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;
    private int[][] board;
    
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
	
    public Winner gameFinished() {
        //TODO Write your implementation for this method
        //columns
        for (int column = 0; column < x; column++) {
          int lastPlayer = -1;
          int count = 0;
          System.out.println("");
          for (int row = 0; row < y; row++) {
            System.out.println(column + ":" + row + " -> " + board[column][row]);
            System.out.println("lastPlayer = " + lastPlayer);
            System.out.println("Count = " + count);
            if (board[column][row] != lastPlayer && lastPlayer > 0 && board[column][row] > 0) {
              lastPlayer = board[column][row];
              count = 1;
            } else if (board[column][row] == lastPlayer) {
              if (count == 3) {
                return getRealWinner(lastPlayer);
              } else {
                count++;
              }
            } else if (board[column][row] > 0) {
              lastPlayer = board[column][row];
              count = 1;
            } else {
              lastPlayer = -1;
              count = 0;
            }
          }
        }

        for (int row = 0; row < y; row++) {
          for (int column = 0; column < x; column++) {
            int lastPlayer = -1;
            int count = 0;
            if (board[column][row] != lastPlayer) {
              lastPlayer = board[column][row];
              count = 1;
            }
            else if (board[column][row] == lastPlayer) {
              if (count == 3) {
                return getRealWinner(lastPlayer);
              } else {
                count++;
              }
            } else {
              lastPlayer = -1;
              count = 0;
            }
          }
        }
        return Winner.NOT_FINISHED;
    }


    public void insertCoin(int column, int playerID) {
        //TODO Write your implementation for this method	
        int[] myColumn = board[column];
        for (int i = 0; i < y; i++) {
          if (myColumn[i] == 0) {
            myColumn[i] = playerID;
            break;
          }
        }
    }

    public int decideNextMove() {
        //TODO Write your implementation for this method
        return 0;
    }

}
