package game.standard;

import game.framework.Direction;
import game.framework.Game;
import game.framework.GameConstants;
import game.framework.Player;

/**
 * Created by fuve on 01/04/2017.
 */
public class GameImpl implements Game {
    private boolean running;

    public GameImpl() {
        running = false;
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
    }

    @Override
    public void setPlayerDirection(Direction d, Player player) {

    }
}
