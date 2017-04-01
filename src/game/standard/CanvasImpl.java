package game.standard;

import game.framework.Canvas;
import game.framework.Controller;
import game.framework.Player;
import game.framework.Position;
import game.local.KeyController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
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
    public void update(List<Player> players) {
        drawPlayers(players);
        drawScores(players);
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
        gc.fillRect(GAME_WIDTH - SCORE_OFFSET_TO_LEFT, 0, SCORE_OFFSET_TO_LEFT, GAME_HEIGHT);

        gc.setFill(Color.BLACK);
        int xCoordinate = GAME_WIDTH - scoreOffSetToLeft + 10;
        Font oldFont = gc.getFont();
        gc.setFont(new Font("Verdana", 25));
        gc.fillText("SCOREBOARD", xCoordinate, scoreOffSetToTop - 55);
        gc.setFont(oldFont);

        gc.fillText("Name:", xCoordinate, scoreOffSetToTop - scoreLineHeight, scoreSpaceForPlayerName);
        gc.fillText("Score:", xCoordinate + scoreSpaceForPlayerName, scoreOffSetToTop - scoreLineHeight, scoreSpaceForPlayerName);
        int xCoordinate = GAME_WIDTH - SCORE_OFFSET_TO_LEFT;
        gc.fillText("Name:", xCoordinate, SCORE_OFFSET_TO_TOP - SCORE_LINE_HEIGHT, SCORE_SPACE_FOR_PLAYER_NAME);
        gc.fillText("Score:", xCoordinate + SCORE_SPACE_FOR_PLAYER_NAME, SCORE_OFFSET_TO_TOP - SCORE_LINE_HEIGHT, SCORE_SPACE_FOR_PLAYER_NAME);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int yCoordinate = SCORE_OFFSET_TO_TOP + (i * SCORE_LINE_HEIGHT);
            gc.fillText(player.getName(), xCoordinate, yCoordinate, SCORE_SPACE_FOR_PLAYER_NAME);
            gc.fillText("" + player.getScore(), xCoordinate + SCORE_SPACE_FOR_PLAYER_NAME, yCoordinate, SCORE_OFFSET_TO_LEFT - SCORE_SPACE_FOR_PLAYER_NAME);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CurvesCurves");
        Group root = new Group();
        GAME_WIDTH = (int) Screen.getPrimary().getBounds().getWidth();
        GAME_HEIGHT = (int) Screen.getPrimary().getBounds().getHeight();

        javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        GameImpl game = new GameImpl(this);
        Controller c = game.addPlayer("SlowBro", Color.RED);
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, (KeyController) c);
        canvas.addEventHandler(KeyEvent.KEY_RELEASED, (KeyController) c);
        canvas.setFocusTraversable(true);
        Thread t = new Thread(game);
        t.start();
        //testMethod(primaryStage);
    }

    /**
     * Method for visually testing the canvas
     *
     * @param primaryStage The stage on which the test is run
     */
    private void testMethod(Stage primaryStage) {
        Thread testThread = new Thread(() -> {
            int i = 0;
            Random random = new Random();

            Position pos1 = new PositionImpl(50, 50);
            Position pos2 = new PositionImpl(50, 100);
            Position pos3 = new PositionImpl(100, 50);
            Position pos4 = new PositionImpl(100, 100);


            while (true) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    return;
                }
                List<Player> players = new ArrayList<>();

                players.add(new PlayerImpl("Player 1", i + random.nextInt(5), pos1, Color.AQUA, 0));
                players.add(new PlayerImpl("Player 2 WITH CRAZILY LONG NAME LIKE REALLY CRAZY!", i + random.nextInt(5), pos2, Color.BLUEVIOLET, 0));
                players.add(new PlayerImpl("Player 3", i + random.nextInt(5), pos3, Color.RED, 0));
                players.add(new PlayerImpl("Player 4", i + random.nextInt(5), pos4, Color.PURPLE, 0));

                update(players);

                pos1 = new PositionImpl(pos1.getX() + random.nextInt(2), pos1.getY() + random.nextInt(2));
                pos2 = new PositionImpl(pos2.getX() + random.nextInt(2), pos2.getY() + random.nextInt(2));
                pos3 = new PositionImpl(pos3.getX() + random.nextInt(2), pos3.getY() + random.nextInt(2));
                pos4 = new PositionImpl(pos4.getX() + random.nextInt(2), pos4.getY() + random.nextInt(2));

                i += 5;
            }
        });
        primaryStage.setOnCloseRequest(event -> testThread.interrupt());
        testThread.start();
    }
}
