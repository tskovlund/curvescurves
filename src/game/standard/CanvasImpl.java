package game.standard;

import game.framework.Canvas;
import game.framework.Player;
import game.framework.Position;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static game.framework.GameConstants.*;

/**
 * Made by Rasmus on 31/03/2017.
 */
public class CanvasImpl extends Application implements Canvas {
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static CanvasImpl canvasImpl = null;
    private GraphicsContext gc;

    public CanvasImpl() {
        setCanvasImpl(this);
    }

    public static CanvasImpl waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return canvasImpl;
    }

    private static void setCanvasImpl(CanvasImpl canvasImpl0) {
        canvasImpl = canvasImpl0;
        latch.countDown();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void update(List<Player> players) {
        if (gc == null) return;
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
        clearScoreBoardArea();
        setupScoreBoardBackground();
        drawScoreBoard(players);
    }

    private void clearScoreBoardArea() {
        // Draw background
        gc.setFill(SCORE_BACKGROUND_COLOR);
        gc.fillRect(WIDTH_MINUS_SCOREBOARD(), 0, SCORE_OFFSET_TO_LEFT, GAME_HEIGHT);

        // Draw line seperating scoreboard and game
        gc.setFill(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(WIDTH_MINUS_SCOREBOARD(), 0, WIDTH_MINUS_SCOREBOARD(), GAME_HEIGHT);
    }

    private void setupScoreBoardBackground() {
        // Draw title
        Font oldFont = gc.getFont();
        gc.setFont(new Font("Verdana", 25));
        gc.fillText("SCOREBOARD", GAME_WIDTH - SCORE_OFFSET_TO_LEFT + 10, SCOREBOARD_TITLE_Y_POS);
        gc.setFont(oldFont);

        // Draw headers
        gc.fillText("Name:", GAME_WIDTH - SCORE_OFFSET_TO_LEFT + 10, SCOREBOARD_HEADERS_Y_POS, SCORE_SPACE_FOR_PLAYER_NAME);
        gc.fillText("Score:", GAME_WIDTH - SCORE_OFFSET_TO_LEFT + 10 + SCORE_SPACE_FOR_PLAYER_NAME, SCOREBOARD_HEADERS_Y_POS, SCORE_SPACE_FOR_PLAYER_NAME);
    }

    private void drawScoreBoard(List<Player> players) {
        // Draw name and score for each player
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int yCoordinate = SCORE_OFFSET_TO_TOP + (i * SCORE_LINE_HEIGHT);
            gc.fillText(player.getName(), GAME_WIDTH - SCORE_OFFSET_TO_LEFT + 10, yCoordinate, SCORE_SPACE_FOR_PLAYER_NAME);
            gc.fillText("" + player.getScore(), GAME_WIDTH - SCORE_OFFSET_TO_LEFT + 10 + SCORE_SPACE_FOR_PLAYER_NAME, yCoordinate, NAME_MAX_WIDTH);
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
//        Driver.main(null);

        /**
         GameImpl game = new GameImpl(this);
         Controller c = game.addPlayer("SlowBro", Color.RED, KeyCode.LEFT, KeyCode.RIGHT);
         Controller c1 = game.addPlayer("BroSlow", Color.BLUE, KeyCode.A, KeyCode.D);
         canvas.addEventHandler(KeyEvent.KEY_PRESSED, (KeyController) c);
         canvas.addEventHandler(KeyEvent.KEY_RELEASED, (KeyController) c);
         canvas.addEventHandler(KeyEvent.KEY_PRESSED, (KeyController) c1);
         canvas.addEventHandler(KeyEvent.KEY_RELEASED, (KeyController) c1);
         canvas.setFocusTraversable(true);
         Thread t = new Thread(game);
         t.start();
         //testMethod(primaryStage);
         **/
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
