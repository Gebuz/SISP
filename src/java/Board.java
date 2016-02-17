public class Board {

  public static final int EMPTY = 0;
  public static final int PLAYER_ONE = 1;
  public static final int PLAYER_TWO = 2;

  public final int[][] board;
  public Board() {
    this(7,6);
  }

  public Board(int width, int height) {
    board = new int[width][height];
  }

  public void act(int column, int player) {
    for (int i = 0; i < board[column].length; i++) {
      if (board[column][i] == 0) {
        board[column][i] = player;
        break;
      }
    }
  }
}
