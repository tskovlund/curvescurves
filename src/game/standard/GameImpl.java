package game.standard;

import game.framework.*;
import game.local.KeyController;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class GameImpl implements Game, Runnable {
    private boolean running;
    private Map<Player, Direction> playerMap;
    private Canvas canvas;
    private Map<Integer, Map<Integer, Set<Long>>> pathMap;

    public GameImpl(Canvas canvas) {
        running = false;
        playerMap = new HashMap<>();
        this.canvas = canvas;
        pathMap = new HashMap<>();
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
        double previous = System.nanoTime() / 1000000;
        double lag = 0.0;
        while (running) {
            double current = System.nanoTime() / 1000000;
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while (lag >= GameConstants.MS_PER_UPDATE) {
                update();
                lag -= GameConstants.MS_PER_UPDATE;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            render();
        }
    }

    private void update() {
        for (Player p : getAlivePlayers()) {
            int a = p.getAngle();

            double deltaX = Math.cos(Math.toRadians(a));
            double deltaY = Math.sin(Math.toRadians(a));

            p.updatePosition(deltaX, deltaY);

            Long timeStamp = System.nanoTime();

            if (!isLegalPosition(p.getPosition(), timeStamp)) {
                p.setAlive(false);
            }

            addToPath(p.getPosition(), timeStamp);

            if (playerMap.get(p) == Direction.LEFT) {
                p.turn(-GameConstants.TURN_SPEED);
            }
            if (playerMap.get(p) == Direction.RIGHT) {
                p.turn(GameConstants.TURN_SPEED);
            }
        }
    }

    private boolean isLegalPosition(Position p, Long timeStamp) {
        int x = (int) p.getX();
        int y = (int) p.getY();

        for (int i = x - 1; i <= x + 1; i++) {
            if (pathMap.containsKey(i)) {
                Map<Integer, Set<Long>> yMap = pathMap.get(i);
                for (int j = y - 1; j <= y + 1; j++) {
                    if (yMap.containsKey(j)) {
                        Set<Long> set = yMap.get(j);
                        for (Long stamp : set) {
                            if (Math.abs(timeStamp - stamp) > GameConstants.MIN_DELTA_TIMESTAMP_NANOSECS) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void render() {
        canvas.update(getAlivePlayers());
    }

    @Override
    public void setPlayerDirection(Direction d, Player player) {
        playerMap.put(player, d);
    }

    @Override
    public Controller addPlayer(String name, Color color, KeyCode left, KeyCode right) {
        Position p = newPlayerPosition();
        addToPath(p, System.nanoTime());
        Player player = new PlayerImpl(name, 0, p, color, randomAngle());
        playerMap.put(player, Direction.FORWARD);
        return new KeyController(this, player, left, right);
    }

    private void addToPath(Position p, Long timeStamp) {
        int x = (int) p.getX();
        int y = (int) p.getY();
        if (pathMap.containsKey(x)) {
            if (pathMap.get(x).containsKey(y)) {
                pathMap.get(x).get(y).add(timeStamp);
            } else {
                Set<Long> set = new HashSet<>();
                set.add(System.nanoTime());
                pathMap.get(x).put(y, set);
            }
        } else {
            Map<Integer, Set<Long>> map = new HashMap<>();
            Set<Long> set = new HashSet<>();
            set.add(timeStamp);
            map.put(y, set);
            pathMap.put(x, map);
        }
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

        int x = r.nextInt(GameConstants.WIDTH_MINUS_SCOREBOARD() - 2 * GameConstants.MIN_INITIAL_PLAYER_DIST) + GameConstants.MIN_INITIAL_PLAYER_DIST;
        int y = r.nextInt(GameConstants.GAME_HEIGHT - 2 * GameConstants.MIN_INITIAL_PLAYER_DIST) + GameConstants.MIN_INITIAL_PLAYER_DIST;

        while (!checkInitialPosition(x, y)) {
            x = r.nextInt(GameConstants.WIDTH_MINUS_SCOREBOARD() - 2 * GameConstants.MIN_INITIAL_PLAYER_DIST) + GameConstants.MIN_INITIAL_PLAYER_DIST;
            y = r.nextInt(GameConstants.GAME_HEIGHT - 2 * GameConstants.MIN_INITIAL_PLAYER_DIST) + GameConstants.MIN_INITIAL_PLAYER_DIST;
        }

        return new PositionImpl(x, y);
    }

    private boolean checkInitialPosition(int x, int y) {
        for (Position p : getPositions()) {
            if (Math.abs(p.getX() - x) < GameConstants.MIN_INITIAL_PLAYER_DIST) {
                return false;
            }
            if (Math.abs(p.getY() - y) < GameConstants.MIN_INITIAL_PLAYER_DIST) {
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

    private List<Player> getAlivePlayers() {
        return getPlayers().stream().filter(Player::isAlive).collect(Collectors.toList());
    }
}
