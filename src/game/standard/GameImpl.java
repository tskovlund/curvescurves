package game.standard;

import game.framework.*;
import game.local.KeyController;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.util.*;

public class GameImpl implements Game, Runnable {
    private boolean running;
    private Map<Player, Direction> playerMap;
    private Canvas canvas;

    public GameImpl(Canvas canvas) {
        running = false;
        playerMap = new HashMap<>();
        this.canvas = canvas;
    }

    public Map<String, Color> getAvailableColors() {
        Map<String, Color> map = new HashMap<>();

        for (Field f : Color.class.getFields())
            try {
                Object obj = f.get(null);
                if (obj instanceof Color) map.put(f.getName(), (Color) obj);
            } catch (IllegalAccessException ignored) {
            }

        List<String> usedColors = new ArrayList<>();
        for (Player player : getPlayers()) {
            for (Map.Entry<String, Color> entry : map.entrySet()) {
                if (entry.getValue().equals(player.getColor())) usedColors.add(entry.getKey());
            }
        }
        for (String s : usedColors) map.remove(s);

        return map;
    }

    @Override
    public Color getAvailableColor() {
        Map<String, Color> colorMap = getAvailableColors();
        List<String> keys = new ArrayList<>(colorMap.keySet());
        String randomColor = keys.get(new Random().nextInt(keys.size()));
        return colorMap.get(randomColor);
    }

    @Override
    public void start() {
        running = true;
        render();
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

            while (lag >= GameConstants.MS_PER_UPDATE) {
                update();
                lag -= GameConstants.MS_PER_UPDATE;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
    public Controller addPlayer(String name, Color color) {
        Player p = new PlayerImpl(name, 0, newPlayerPosition(), color, randomAngle());
        playerMap.put(p, Direction.FORWARD);
        return new KeyController(this, p);
    }

    private int randomAngle() {
        return new Random().nextInt(360);
    }

    @Override
    public Player getPlayer(String name) {
        for (Player p : playerMap.keySet()) {
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

        return new PositionImpl(x, y);
    }

    private boolean checkPosition(int x, int y) {
        for (Position p : getPositions()) {
            if (Math.abs(p.getX() - x) < GameConstants.MIN_INITIAL_DIST) {
                return false;
            }
            if (Math.abs(p.getY() - y) < GameConstants.MIN_INITIAL_DIST) {
                return false;
            }
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

    @Override
    public void run() {
        start();
    }
}
