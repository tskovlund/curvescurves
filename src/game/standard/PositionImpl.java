package game.standard;

import game.framework.Position;

/**
 * Created by fuve on 01/04/2017.
 */
public class PositionImpl implements Position {
    private double x;
    private double y;

    public PositionImpl(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position: " + "x=" + x + ", y=" + y;
    }
}
