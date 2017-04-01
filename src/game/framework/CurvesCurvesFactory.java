package game.framework;

import game.standard.GameImpl;
import javafx.scene.paint.Color;

/**
 * Created by fuve on 01/04/2017.
 */
public interface CurvesCurvesFactory {
    Canvas createCanvas();

    Controller createController(GameImpl game, Player player);

    Player createPlayer(String name, Position p, Color color, int i);
}
