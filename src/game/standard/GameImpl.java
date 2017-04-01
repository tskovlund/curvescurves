package game.standard;

import game.framework.*;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Created by fuve on 01/04/2017.
 */
public class GameImpl implements Game {
    private boolean running;
    private Map<Player, Direction> playerMap;
    private Canvas canvas;

    public GameImpl(Canvas canvas) {
        running = false;
        playerMap = new HashMap<>();
        this.canvas = canvas;
    }

    @Override
    public void start() {
        running = true;
        mainLoop();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void mainLoop() {
        double previous = System.nanoTime();
        double lag = 0.0;
        while (running) {
            double current = System.nanoTime();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while (lag >= GameConstants.MS_PER_UPDATE)
            {
                update();
                lag -= GameConstants.MS_PER_UPDATE;
            }

            render();
        }
    }

    private void update() {
    }

    private void render() {
        canvas.update(getPlayers());
    }

    @Override
    public void setPlayerDirection(Direction d, Player player) {
        playerMap.put(player, d);
    }

    @Override
    public void addPlayer(String name, Color color) {
        playerMap.put(new PlayerImpl(name, 0, newPlayerPosition(), color), Direction.FORWARD);
    }

    private Position newPlayerPosition() {
        Random r = new Random();

        int x = r.nextInt(GameConstants.GAME_HEIGHT);
        int y = r.nextInt(GameConstants.GAME_WIDTH);

        while (!checkPosition(x,y)) {
            x = r.nextInt(GameConstants.GAME_HEIGHT);
            y = r.nextInt(GameConstants.GAME_WIDTH);
        }

        return new PositionImpl(x,y);
    }

    private boolean checkPosition(int x, int y) {
        for (Position p : getPositions()) {
            if (Math.abs(p.getX() - x) < GameConstants.MIN_INITIAL_DIST) { return false; }
            if (Math.abs(p.getY() - y) < GameConstants.MIN_INITIAL_DIST) { return false; }
        }
        return true;
    }

    private List<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        for (Player p : getPlayers()) {
            positions.add(p.getPosition());
        }
        return positions;
    }

    private List<Player> getPlayers() {
        return new ArrayList<>(playerMap.keySet());
    }
}
