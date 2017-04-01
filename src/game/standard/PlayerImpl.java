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

    public PlayerImpl(String name, Position position, Color color) {
        this.name = name;
        this.score = 0;
        this.position = position;
        this.color = color;
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
    public Color getColor() {
        return color;
    }
}
