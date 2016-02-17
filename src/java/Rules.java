import java.util.List;
import java.util.ArrayList;

public class Rules {
  private Rules() {}

  public static int whoWon(Board board) {
    for (int i = 0; i < board.board.length; i++) {
      int winner = getWinnerOnLine(board.board[i]);
      if (winner > 0)
        return winner;
    }
    return getWinnerHorizontal(board.board);
  }

  private static int getWinnerHorizontal(int[][] board) {
    for (int i = 0; i < board[0].length; i++) {
      int currentPlayer = 0;
      int connected = 0;
      for (int j = 0; j < board.length; j++) {
        if (board[j][i] != Board.EMPTY) {
          if (currentPlayer == 0) {
            currentPlayer = board[j][i];
            connected += 1;
          } else if (board[j][i] == currentPlayer) {
            connected += 1;
          } else {
            currentPlayer = board[j][i];
            connected = 0;
          }
          if (connected >= 4)
            return currentPlayer;
        }
      }
    }

    return 0;
  }

  private static int getWinnerOnLine(int[] line) {
    int currentPlayer = 0;
    int connected = 0;
    for (int i = 0; i < line.length; i++) {
      if (line[i] != Board.EMPTY) {
        if (currentPlayer == 0) {
          currentPlayer = line[i];
          connected += 1;
        } else if (line[i] == currentPlayer) {
          connected += 1;
        } else {
          currentPlayer = line[i];
          connected = 0;
        }
        if (connected >= 4)
          return currentPlayer;
      }
    }
    return 0;
  }

  public static List<Integer> getActions(Board board) {
    List<Integer> actions = new ArrayList<>();
    for (int i = 0; i < board.board.length; i++) {
      for (int j = board.board[i].length-1; j >= 0; j--) {
        if (board.board[i][j] == 0) {
          actions.add(i);
          break;
        }
      }
    }
    return actions;
  }
}
