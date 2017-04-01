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

    public PlayerImpl(String name, int score, Position position, Color color, int angle) {
        this.name = name;
        this.score = score;
        this.position = position;
        this.color = color;
        this.angle = angle;
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
    public void updatePosition(int deltaX, int deltaY) {
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
    public void turn(int degrees) {
        angle = (angle + degrees) % 360;
        if (angle == -1) angle = 359;
    }
}
