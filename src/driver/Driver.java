package driver;

import game.framework.*;
import game.local.KeyController;
import game.standard.CanvasImpl;
import game.standard.GameImpl;
import game.standard.PlayerImpl;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import server.BaseServer;
import server.ServerToGameAdapter;

import java.util.Scanner;

/**
 * Created by fuve on 01/04/2017.
 */
public class Driver {
    public static void main(String[] args) {
        Game game = new GameImpl(new CurvesCurvesFactory() {
            @Override
            public Canvas createCanvas() {
                return new CanvasImpl();
            }

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

        Scanner s = new Scanner(System.in);
        System.out.println(s.next());
        game.start();
    }
}
