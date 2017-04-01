package game.local;

import game.framework.Controller;
import game.framework.Direction;
import game.framework.Game;
import game.framework.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by fuve on 01/04/2017.
 */
public class KeyController implements Controller, KeyListener {
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

    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_LEFT: left = true;
            case KeyEvent.VK_RIGHT: right = true; break;
        }

        updateDirection();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_LEFT: left = false;
            case KeyEvent.VK_RIGHT: right = false; break;
        }

        updateDirection();
    }

    private void updateDirection() {
        if (left && right) { move(Direction.FORWARD); }
        else if (left) { move(Direction.LEFT); }
        else if (right) { move(Direction.RIGHT); }
        else { move(Direction.FORWARD); }
    }
}
