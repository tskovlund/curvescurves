package game.standard;

import game.framework.*;
import game.local.KeyController;
import javafx.scene.paint.Color;

import java.security.Key;
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
        double previous = System.nanoTime()/1000000;
        double lag = 0.0;
        while (running) {
            double current = System.nanoTime()/1000000;
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
        for (Player p : getPlayers()) {
            int deltaX = 0;
            int deltaY = 0;

            int a = p.getAngle();

            if (a > 292.5 || a < 67.5) deltaX = -1;
            if (a > 112.5 && a < 247.5) deltaX = 1;
            if (a > 22.5 && a < 157.5) deltaY = 1;
            if (a > 205.5 && a < 337.5) deltaY = -1;

            p.updatePosition(deltaX, deltaY);

            if (playerMap.get(p) == Direction.LEFT) {
                p.turn(-GameConstants.TURN_SPEED);
            }
            if (playerMap.get(p) == Direction.RIGHT) {
                p.turn(GameConstants.TURN_SPEED);
            }
        }
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
        Player p = new PlayerImpl(name, 0, newPlayerPosition(), color, randomAngle());
        playerMap.put(p, Direction.FORWARD);
        new KeyController(this, p);
    }

    private int randomAngle() {
        return new Random().nextInt(360);
    }

    @Override
    public Color getAvailableColor() {
        return null;
    }

    @Override
    public Player getPlayer(String name) {
        for (Player p : playerMap.keySet()){
            if (p.getName().equals(name)) return p;
        }
        return null;
    }

    @Override
    public Map<Player, Direction> getPlayerMap() {
        return playerMap;
    }

    private Position newPlayerPosition() {
        Random r = new Random();

        int x = r.nextInt(GameConstants.GAME_HEIGHT - 2*GameConstants.MIN_INITIAL_DIST) + GameConstants.MIN_INITIAL_DIST;
        int y = r.nextInt(GameConstants.GAME_WIDTH - 2*GameConstants.MIN_INITIAL_DIST) + GameConstants.MIN_INITIAL_DIST;

        while (!checkPosition(x,y)) {
            x = r.nextInt(GameConstants.GAME_HEIGHT - 2*GameConstants.MIN_INITIAL_DIST) + GameConstants.MIN_INITIAL_DIST;
            y = r.nextInt(GameConstants.GAME_WIDTH - 2*GameConstants.MIN_INITIAL_DIST) + GameConstants.MIN_INITIAL_DIST;
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
