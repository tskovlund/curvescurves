package game.standard;

import game.framework.Position;

/**
 * Created by fuve on 01/04/2017.
 */
public class PositionImpl implements Position {
    private int x;
    private int y;

    public PositionImpl(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
