package game.framework;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Made by Rasmus on 01/04/2017.
 */
public class GameConstants {
    public static final int PLAYER_DOT_DIAMETER = 3;
    public static final double TURN_SPEED = 2;
    public static int GAME_WIDTH = 1200;
    public static int GAME_HEIGHT = 750;
    public static final Color BACKGROUND_COLOR = Color.LIGHTSALMON;
    public static final Color SCORE_BACKGROUND_COLOR = Color.BISQUE;

    ////
    //    SCORE BOARD CONSTANTS
    ////
    public static final int scoreOffSetToLeft = 200;
    public static final int scoreOffSetToTop = 100;
    public static final int scoreLineHeight = 20;
    public static final int scoreSpaceForPlayerName = 130 -10;

    public static final double MS_PER_UPDATE = 7.5;
    public static final int MIN_INITIAL_DIST = 50;
}
