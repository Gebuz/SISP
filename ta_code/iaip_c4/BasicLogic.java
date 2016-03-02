public class BasicLogic {
  public BasicLogic() {}

  public static IGameLogic.Winner getRealWinner(int id) {
    if (id == 1)
      return IGameLogic.Winner.PLAYER1;
    else
      return IGameLogic.Winner.PLAYER2;
  }

  public static IGameLogic.Winner gameStatus(int[][] board) {
    //TODO Write your implementation for this method
    //columns
    for(int c = 0; c < board.length; c++){
      for(int r = 0; r < board[c].length; r++){
        int player = board[c][r];
        if(player == 0){
          break;
        }
        //check vertical
        if(r+3<board[0].length){
          for(int a = 1; a<4; a++){
            if(player != board[c][r+a]){
              break;
            }
            else if(a == 3){
              return getRealWinner(player);
            }
          }
        }
        //check horizontal
        if(c+3<board.length){
          for(int a = 1; a<4; a++){
            if(player != board[c+a][r]){
              break;
            }
            else if(a == 3){
              return getRealWinner(player);
            }
          }
        }
        //check diagonalup
        if(r+3<board[0].length && c+3<board.length){
          for(int a = 1; a<4; a++){
            if(player != board[c+a][r+a]){
              break;
            }
            else if(a == 3){
              return getRealWinner(player);
            }
          }
        }
        //check diagonaldown
        if(r-3>0 && c+3<board.length){
          for(int a = 1; a<4; a++){
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
    for (int column = 0; column < board.length; column++) {
      if (board[column][board[0].length-1] == 0)
        return IGameLogic.Winner.NOT_FINISHED;
    }

    return IGameLogic.Winner.TIE;
  }

  public static int[][] copyOf(int[][] state) {
    int[][] copy = new int[state.length][state[0].length];
    for (int column = 0; column < state.length; column++)
      for (int row = 0; row < state[column].length; row++)
        copy[column][row] = state[column][row];
    return copy;
  }
}
