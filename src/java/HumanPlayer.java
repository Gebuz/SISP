public class HumanPlayer implements Player {
  public HumanPlayer() {
  }

  @Override
  public int act(Board board) {
    while (View.x == -1) {
      System.out.println("Waiting...");
      try {
        Thread.sleep(1000);
      } catch (Exception e) {}
    }
    int column = View.x;
    View.x = -1;
    return column;
  }
}
