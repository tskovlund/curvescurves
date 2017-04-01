package game.local;

import game.framework.Controller;
import game.framework.Direction;
import game.framework.Game;
import game.framework.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Created by fuve on 01/04/2017.
 */
public class KeyController implements Controller, EventHandler<KeyEvent> {
    private final Player player;
    private final Game game;

    private boolean left;
    private boolean right;

    public KeyController(Game g, Player p) {
        game = g;
        player = p;
    }

    @Override
    public void move(Direction d) {
        game.setPlayerDirection(d, player);
    }

    private void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();

        switch (code) {
            case LEFT: left = true;
            case RIGHT: right = true; break;
        }

        updateDirection();
    }

    private void keyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        switch (code) {
            case LEFT: left = false;
            case RIGHT: right = false; break;
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
