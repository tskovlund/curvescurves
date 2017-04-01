package driver;

import game.framework.*;
import game.local.KeyController;
import game.standard.GameImpl;
import game.standard.PlayerImpl;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import server.BaseServer;
import server.ServerToGameAdapter;


public class Driver {
    public static void main(String[] args) {
        Game game = new GameImpl((name, p, color, angle) -> new PlayerImpl(name, 0, p, color, angle));

        BaseServer server = new BaseServer(new ServerToGameAdapter(), game);
        new Thread(server::start).start();

//        game.addPlayer("1", Color.AQUA);
//        game.addPlayer("2", Color.BEIGE);
//        game.addPlayer("3", Color.BLUE);
//        game.addPlayer("4", Color.WHITE);
//        game.addPlayer("5", Color.PURPLE);

        while (game.getPlayerMap().size() < 3) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("LET THE GAMES BEGIN");

        game.start();
    }
}
