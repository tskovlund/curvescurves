package game.framework;

import javafx.scene.paint.Color;

/**
 * Created by fuve on 31/03/2017.
 */
public interface Player {
    String getName();
    int getScore();
    Position getPosition();
    void updatePosition(double deltaX, double deltaY);
    void setPosition(Position p);
    Color getColor();
    int getAngle();
    void turn(double degrees);
    boolean isAlive();
    void setAlive(boolean alive);
    void incrementScore();
}
