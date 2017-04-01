package game.framework;

import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Created by fuve on 31/03/2017.
 */
public interface Game {
    void start();
    void stop();
    void mainLoop();
    void setPlayerDirection(Direction d, Player player);
    Controller addPlayer(String name, Color color);
    Color getAvailableColor();
    Player getPlayer(String name);
    Map<Player,Direction> getPlayerMap();
}
