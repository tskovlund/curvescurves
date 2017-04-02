package game.framework;

import java.util.List;

/**
 * Created by fuve on 31/03/2017.
 */
public interface Canvas {
    void update(List<Player> players);

    void redrawPlayers(List<Player> players);
}
