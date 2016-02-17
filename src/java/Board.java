import java.util.Arrays;

public class Board {

  public static final int EMPTY = 0;
  public static final int PLAYER_ONE = 1;
  public static final int PLAYER_TWO = 2;

  public final int[][] board;
  public Board() {
    this(7,6);
  }

  public Board(int[][] board) {
    this.board = board;
  }

  public Board(int width, int height) {
    board = new int[width][height];
  }

  public void act(int column, int player) {
    for (int i = 0; i < board[column].length; i++) {
      if (board[column][i] == Board.EMPTY) {
        board[column][i] = player;
        return;
      }
    }
    throw new RuntimeException("No empty space!");
  }

  public Board clone() {
    int[][] newBoard = new int[board.length][0];
    for (int i = 0; i < newBoard.length; i++)
      newBoard[i] = Arrays.copyOf(board[i], board[i].length);
    return new Board(newBoard);
  }
}
