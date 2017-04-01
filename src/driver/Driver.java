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
        Game game = new GameImpl(new CurvesCurvesFactory() {
            @Override
            public Controller createController(GameImpl game, Player player) {
                return new KeyController(game, player, KeyCode.LEFT, KeyCode.RIGHT);
            }

            @Override
            public Player createPlayer(String name, Position p, Color color, int angle) {
                return new PlayerImpl(name, 0, p, color, angle);
            }
        });

        BaseServer server = new BaseServer(new ServerToGameAdapter(), game);
        server.start();

        while (game.getPlayerMap().size() <= 3) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("LET THE GAMES BEGIN");
        Thread t = new Thread(game);
        t.start();
    }
}
