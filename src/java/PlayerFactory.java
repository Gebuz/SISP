public class PlayerFactory {
  private PlayerFactory() {}

  public static Player getPlayer(PlayerType type) {
    switch (type) {
      case RANDOM:
        return new RandomPlayer();
      default:
        break;
    }
    return null;
  }
}
