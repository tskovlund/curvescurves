package game.standard;

import game.framework.Canvas;
import game.framework.GameState;
import game.framework.Player;
import game.framework.Position;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game.framework.GameConstants.*;

/**
 * Made by Rasmus on 31/03/2017.
 */
public class CanvasImpl extends Application implements Canvas {
    private GraphicsContext gc;

    @Override
    public void update(GameState gs) {
        drawPlayers(gs.getPlayers());
        drawScores(gs.getPlayers());
    }

    private void drawPlayers(List<Player> players) {
        for (Player player : players) {
            gc.setFill(player.getColor());
            Position position = player.getPosition();
            gc.fillOval(position.getX(), position.getY(), PLAYER_DOT_DIAMETER, PLAYER_DOT_DIAMETER);
        }
    }

    private void drawScores(List<Player> players) {
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(GAME_WIDTH - scoreOffSetToLeft, scoreOffSetToTop-scoreLineHeight, scoreOffSetToLeft, GAME_HEIGHT - scoreOffSetToTop);

        gc.setFill(Color.BLACK);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int yCoordinate = scoreOffSetToTop + (i * scoreLineHeight);
            int xCoordinate = GAME_WIDTH - scoreOffSetToLeft;
            gc.fillText(player.getName(), xCoordinate, yCoordinate, scoreSpaceForPlayerName);
            gc.fillText("" + player.getScore(), xCoordinate + scoreSpaceForPlayerName, yCoordinate, scoreOffSetToLeft - scoreSpaceForPlayerName);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CurvesCurves");
        Group root = new Group();
        javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        testMethod();
    }


    private void testMethod() {
        new Thread(() -> {
            int i = 0;

            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random random = new Random();
                List<Player> players = new ArrayList<>();
                int moveStep = 5;
                players.add(new PlayerImpl("Player 1", i + random.nextInt(5), new PositionImpl(50+random.nextInt(moveStep), 50+random.nextInt(moveStep)), Color.AQUA));
                players.add(new PlayerImpl("Player 2 WITH CRAZILY LONG NAME LIKE REALLY CRAZY!", i + random.nextInt(moveStep), new PositionImpl(100+random.nextInt(moveStep), 50+random.nextInt(2)), Color.BLUEVIOLET));
                players.add(new PlayerImpl("Player 3", i + random.nextInt(5), new PositionImpl(50+random.nextInt(moveStep), 100+random.nextInt(moveStep)), Color.RED));
                players.add(new PlayerImpl("Player 4", i + random.nextInt(5), new PositionImpl(100+random.nextInt(moveStep), 100+random.nextInt(moveStep)), Color.PURPLE));

                update(() -> players);
                i += 5;
            }
        }).start();
    }
}
