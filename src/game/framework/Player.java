package game.framework;

import javafx.scene.paint.Color;

/**
 * Created by fuve on 31/03/2017.
 */
public interface Player {
    String getName();
    int getScore();
    Position getPosition();
    void updatePosition(int deltaX, int deltaY);
    Color getColor();
    int getAngle();
    void turn(int degrees);
}
