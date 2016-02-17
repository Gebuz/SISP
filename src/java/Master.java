public class Master {
  public static void main(String[] args) {
    Player player1 = PlayerFactory.getPlayer(PlayerType.HUMAN);
    Player player2 = PlayerFactory.getPlayer(PlayerType.HUMAN);

    final Board board = new Board();

    Thread t = new Thread() {
      public void run() {
        View.hue(board);
      }
    };
    t.start();
    while (Rules.whoWon(board) == 0) {
      int p1 = player1.act(board);
      board.act(p1, Board.PLAYER_ONE);
      System.out.println("Player 1's move: " + p1);
      int p2 = player2.act(board);
      board.act(p2, Board.PLAYER_TWO);
      System.out.println("Player 2's move: " + p2);
    }

    System.out.println("Winner is player " + Rules.whoWon(board));
  }
}
