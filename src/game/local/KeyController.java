package game.local;

import game.framework.Direction;
import game.framework.Game;
import game.framework.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by fuve on 01/04/2017.
 */
public class KeyController implements EventHandler<KeyEvent> {
    private final Player player;
    private final Game game;

    private boolean left;
    private boolean right;

    private final KeyCode leftKey;
    private final KeyCode rightKey;

    public KeyController(Game g, Player p, KeyCode left, KeyCode right) {
        game = g;
        player = p;
        leftKey = left;
        rightKey = right;
    }

    private void move(Direction d) {
        game.setPlayerDirection(d, player);
    }

    private void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code.equals(leftKey)) {
            left = true;
        } else if (code.equals(rightKey)) {
            right = true;
        }

        updateDirection();
    }

    private void keyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code.equals(leftKey)) {
            left = false;
        } else if (code.equals(rightKey)) {
            right = false;
        }

        updateDirection();
    }

    private void updateDirection() {
        if (left && right) { move(Direction.FORWARD); }
        else if (left) { move(Direction.LEFT); }
        else if (right) { move(Direction.RIGHT); }
        else { move(Direction.FORWARD); }
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            keyPressed(event);
        }

        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            keyReleased(event);
        }
    }
}
