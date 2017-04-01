package game.framework;

import javafx.scene.paint.Color;

/**
 * Made by Rasmus on 01/04/2017.
 */
public class GameConstants {
    ////
    //    GAME CONSTANTS
    ////
    public static final int PLAYER_DOT_DIAMETER = 3;
    public static final Color BACKGROUND_COLOR = Color.LIGHTSALMON;

    ////
    //    SCORE BOARD CONSTANTS
    ////
    public static final Color SCORE_BACKGROUND_COLOR = Color.BISQUE;
    public static final int SCORE_OFFSET_TO_LEFT = 200;
    public static final int SCORE_OFFSET_TO_TOP = 100;
    public static final int SCORE_LINE_HEIGHT = 20;
    public static final int SCORE_SPACE_FOR_PLAYER_NAME = 130;
    public static final int SCOREBOARD_TITLE_Y_POS = SCORE_OFFSET_TO_TOP - 55;
    public static final int SCOREBOARD_HEADERS_Y_POS = SCORE_OFFSET_TO_TOP - SCORE_LINE_HEIGHT;
    public static final int NAME_MAX_WIDTH = SCORE_OFFSET_TO_LEFT - SCORE_SPACE_FOR_PLAYER_NAME;

    ////
    //    CONTROL/TIME CONSTANTS
    ////
    public static final double MS_PER_UPDATE = 10;
    public static final double TURN_SPEED = 1;
    public static final int MIN_INITIAL_PLAYER_DIST = 50;
    public static final long MIN_DELTA_TIMESTAMP_NANOSECS = 500000000;
    public static final int GAP_MODULO = 600;
    public static final int GAP_SIZE = 30;

    ////
    //    GAME SIZE CONSTANTS
    ////
    public static int GAME_WIDTH = 1000;
    public static int GAME_HEIGHT = 400;

    public static int WIDTH_MINUS_SCOREBOARD() {
        return GAME_WIDTH - SCORE_OFFSET_TO_LEFT;
    }
}
