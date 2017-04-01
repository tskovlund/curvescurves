package game.framework;

/**
 * Created by fuve on 31/03/2017.
 */
public interface Game {
    void start();
    void stop();
    void mainLoop();
    void setPlayerDirection(Direction d, Player player);
}
