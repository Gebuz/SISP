public class HumanPlayer implements Player {
  public HumanPlayer() {
  }

  @Override
  public int act(Board board) {
    while (View.x == -1);
    int column = View.x;
    View.x = -1;
    return column;
  }
}
