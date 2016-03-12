import java.util.ArrayList;

public final class Heuristic{
  private Heuristic() {

  }

  private static final boolean DEBUG = false;

  private static final double EMPTY_SPACE_VALUE = 0.5;

  private static int opponent(int player) {
    if(player == 1)
      return 2;
    else return 1;
  }

  public static double GetHeuristic(int[][] board, int player) {
    if (DEBUG)
      System.err.println("---------------------------------------------");
    int x = board.length;
    int y = board[0].length;
    double sum = 0;
    int[][] traps = new int[x][y];
    for(int c = 0; c < x; c++) {
      for(int r = 0; r < y; r++) {
        int startp = board[c][r];
        //check vertical
        if (r+3<y) {
          double count = 0;
          for(int a = 0; a<4; a++) {
            int currentp = board[c][r+a];
            if(startp == player && currentp == player) {
              count+=1;
            } else if(startp == player && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
            } else if(startp == opponent(player) && currentp == 0) {
              count-=EMPTY_SPACE_VALUE;
            } else if(startp == opponent(player) && currentp == opponent(player)) {
              count-=1;
            } else if(startp == 0 && currentp == player) {
              count+=1;
              startp = currentp;
            } else if(startp == 0 && currentp == opponent(player)) {
              count = -count;
              count-=1;
              startp = currentp;
            } else if(startp == 0 && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
            } else{
              count = 0;
              break;
            }
          }
          if (count >= EMPTY_SPACE_VALUE*4 || count <= -EMPTY_SPACE_VALUE*4) {
            sum += count;
          }
        }
        if (DEBUG)
          System.err.println("Sum after vertical: " + sum);
        //check horizontal
        if(c+3<x) {
          double count = 0;
          int empty = -1;
          for(int a = 0; a<4; a++) {
            int currentp = board[c+a][r];
            if(startp == player && currentp == player) {
              count+=1;
            } else if(startp == player && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
              empty = a;
            } else if(startp == opponent(player) && currentp == 0) {
              count-=EMPTY_SPACE_VALUE;
              empty = a;
            } else if(startp == opponent(player) && currentp == opponent(player)) {
              count-=1;
            } else if(startp == 0 && currentp == player) {
              count+=1;
              startp = currentp;
            } else if(startp == 0 && currentp == opponent(player)) {
              count = -count;
              count-=1;
              startp = currentp;
            } else if(startp == 0 && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
              empty = 0;
            } else{
              count = 0;
              break;
            }
          }
          if (count >= EMPTY_SPACE_VALUE*4 || count <= -EMPTY_SPACE_VALUE*4) {
            sum += count;
          }
          if (DEBUG)
            System.err.println("Sum after horizontal: " + sum);
          if (count >= 3+EMPTY_SPACE_VALUE) {
            if(r-1 >= 0 && board[c+empty][r-1] == 0){
              if(traps[c+empty][r-1] == 2){
                traps[c+empty][r-1] = 3;
              } else{
                traps[c+empty][r-1] = 1;
              }
            }
          }
          if (count <= -3-EMPTY_SPACE_VALUE) {
            if(r-1 >= 0 && board[c+empty][r-1] == 0){
              if(traps[c+empty][r-1] == 1){
                traps[c+empty][r-1] = 3;
              } else{
                traps[c+empty][r-1] = 2;
              }
            }
          }
        }
        //check diagonalup
        if(r+3<y && c+3<x) {
          double count = 0;
          int empty = -1;
          for(int a = 0; a<4; a++) {
            int currentp = board[c+a][r+a];
            if(startp == player && currentp == player) {
              count+=1;
            } else if(startp == player && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
              empty = a;
            } else if(startp == opponent(player) && currentp == 0) {
              count-=EMPTY_SPACE_VALUE;
              empty = a;
            } else if(startp == opponent(player) && currentp == opponent(player)) {
              count-=1;
            } else if(startp == 0 && currentp == player) {
              count+=1;
              startp = currentp;
            } else if(startp == 0 && currentp == opponent(player)) {
              count = -count;
              count-=1;
              startp = currentp;
            } else if(startp == 0 && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
              empty = a;
            } else{
              count = 0;
              break;
            }
          }
          if (count >= EMPTY_SPACE_VALUE*4 || count <= -EMPTY_SPACE_VALUE*4) {
            sum += count;
          }
          if (DEBUG)
            System.err.println("Sum after diagonal up: " + sum);
          if (count >= 3+EMPTY_SPACE_VALUE) {
            if(r+empty-1 >= 0 && board[c+empty][r+empty-1] == 0){
              if(traps[c+empty][r+empty-1] == 2){
                traps[c+empty][r+empty-1] = 3;
              } else{
                traps[c+empty][r+empty-1] = 1;
              }
            }
          }
          if (count <= -3-EMPTY_SPACE_VALUE) {
            if(r+empty-1 >= 0 && board[c+empty][r+empty-1] == 0){
              if(traps[c+empty][r+empty-1] == 1){
                traps[c+empty][r+empty-1] = 3;
              } else{
                traps[c+empty][r+empty-1] = 3;
              }
            }
          }
        }
        //check diagonaldown
        if(r-3>=0 && c+3<x) {
          double count = 0;
          int empty = -1;
          for(int a = 0; a<4; a++) {
            int currentp = board[c+a][r-a];
            if(startp == player && currentp == player) {
              count+=1;
            } else if(startp == player && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
              empty = a;
            } else if(startp == opponent(player) && currentp == 0) {
              count-=EMPTY_SPACE_VALUE;
              empty = a;
            } else if(startp == opponent(player) && currentp == opponent(player)) {
              count-=1;
            } else if(startp == 0 && currentp == player) {
              count+=1;
              startp = currentp;
            } else if(startp == 0 && currentp == opponent(player)) {
              count = -count;
              count-=1;
              startp = currentp;
            } else if(startp == 0 && currentp == 0) {
              count+=EMPTY_SPACE_VALUE;
              empty = a;
            } else{
              count = 0;
              break;
            }
          }
          if (count >= EMPTY_SPACE_VALUE*4 || count <= -EMPTY_SPACE_VALUE*4) {
            sum += count;
          }
          if (DEBUG)
            System.err.println("Sum after diagonal down: " + sum);
          if (count >= 3+EMPTY_SPACE_VALUE) {
            if(r-empty-1 >= 0 && board[c+empty][r-empty-1] == 0){
              if(traps[c+empty][r-empty-1] == 2){
                traps[c+empty][r-empty-1] = 3;
              } else{
                traps[c+empty][r-empty-1] = 1;
              }
            }
          }
          if (count <= -3-EMPTY_SPACE_VALUE) {
            if(r-empty-1 >= 0 && board[c+empty][r-empty-1] == 0){
              if(traps[c+empty][r-empty-1] == 1){
                traps[c+empty][r-empty-1] = 3;
              } else{
                traps[c+empty][r-empty-1] = 2;
              }
            }
          }
        }
      }
    }
    if (DEBUG)
      System.err.println("Sum before traps: " + sum);
    //check traps
    for(int c = 0; c < x; c++){
      int countTraps = 0;
      for(int r = 0; r < y; r++){
        if(traps[c][r] == 1){
          if(countTraps == 0){
            if(traps[c][r+1] == 1){
              return 1000;
            }
            sum += 500;
            countTraps += 1;
          } else{
            sum += 50;
          }
        }
        if(traps[c][r] == 2){
          if(countTraps == 0){
            if(traps[c][r+1] == 2){
              return -1000;
            }
            sum -= 500;
            countTraps += 1;
          } else{
            sum -= 50;
          }
        }
      }
    }
    if (DEBUG) {
      for (int row = board[0].length-1; row >= 0; row--) {
        for (int col = 0; col < board.length; col++) {
          System.err.print(board[col][row] + " ");
        }
        System.err.println();
      }
      System.err.println("Player " + player + " -> Sum = " + sum);
    }
    return sum;
  }
}
