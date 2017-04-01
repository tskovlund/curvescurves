package game.standard;

import game.framework.Player;
import game.framework.Position;
import javafx.scene.paint.Color;

/**
 * Created by fuve on 01/04/2017.
 */
public class PlayerImpl implements Player {
    private String name;
    private int score;
    private Position position;
    private Color color;
    private int angle;
    private boolean alive;

    public PlayerImpl(String name, int score, Position position, Color color, int angle) {
        this.name = name;
        this.score = score;
        this.position = position;
        this.color = color;
        this.angle = angle;
        this.alive = true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void updatePosition(double deltaX, double deltaY) {
        position = new PositionImpl(position.getX() + deltaX, position.getY() + deltaY);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getAngle() {
        return angle;
    }

    @Override
    public void turn(double degrees) {
        angle = (angle + (int) degrees) % 360;
        if (angle < 0) angle = 360 + angle;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void incrementScore() {
        score++;
    }
}
