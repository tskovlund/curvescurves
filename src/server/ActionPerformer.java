package server;

import game.framework.Game;
import game.framework.Player;

public interface ActionPerformer {

    public void perform(String s, Game game, Player player)throws ReflectiveOperationException;
}
