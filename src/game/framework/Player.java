package game.framework;

import javafx.scene.paint.Color;

/**
 * Created by fuve on 31/03/2017.
 */
public interface Player {
    String getName();
    int getScore();
    Position getPosition();
    Color getColor();
}
