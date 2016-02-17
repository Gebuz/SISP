public class Master {
  public static void main(String[] args) {
    Player player1 = null;
    Player player2 = null;

    Board board = new Board();
    while (Rules.whoWon(board) == 0) {
      board.act(player1.act(board), 1);
      board.act(player2.act(board), 2);
    }

    System.out.println("Winner is player " + Rules.whoWon(board));
  }
}
