
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
	
    public Winner gameFinished() {
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
        return count++%x;
    }

}
