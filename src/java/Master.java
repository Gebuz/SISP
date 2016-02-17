public class Master {
  public static void main(String[] args) {
    Player player1 = PlayerFactory.getPlayer(PlayerType.HUMAN);
    Player player2 = PlayerFactory.getPlayer(PlayerType.HUMAN);

    Board board = new Board();
    while (Rules.whoWon(board) == 0) {
      board.act(player1.act(board), Board.PLAYER_ONE);
      board.act(player2.act(board), Board.PLAYER_TWO);
    }

    System.out.println("Winner is player " + Rules.whoWon(board));
  }
}
