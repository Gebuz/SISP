import java.util.Random;
import java.util.List;

public class RandomPlayer implements Player {

  private final Random random;

  public RandomPlayer() {
    random = new Random();
  }

  @Override
  public int act(Board board) {
    List<Integer> actions = Rules.getActions(board);
    return random.nextInt(actions.size());
  }
}
