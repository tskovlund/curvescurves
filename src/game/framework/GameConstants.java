package game.framework;

import javafx.scene.paint.Color;

/**
 * Made by Rasmus on 01/04/2017.
 */
public class GameConstants {
    public static final int PLAYER_DOT_DIAMETER = 3;
    public static int GAME_WIDTH = 1200;
    public static int GAME_HEIGHT = 750;
    public static final Color BACKGROUND_COLOR = Color.LIGHTSALMON;

    public static int WIDTH_MINUS_SCOREBOARD() {
        return GAME_WIDTH - SCORE_OFFSET_TO_LEFT;
    }

    ////
    //    SCORE BOARD CONSTANTS
    ////
    public static final int SCORE_OFFSET_TO_LEFT = 200;
    public static final int SCORE_OFFSET_TO_TOP = 50;
    public static final int SCORE_LINE_HEIGHT = 20;
    public static final int SCORE_SPACE_FOR_PLAYER_NAME = 130;

    ////
    //    CONTROL/TIME CONSTANTS
    ////
    public static final double MS_PER_UPDATE = 7.5;
    public static final double TURN_SPEED = 2;

    public static final int MIN_INITIAL_PLAYER_DIST = 50;
}
