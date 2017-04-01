package server.Stubs;

import game.framework.Game;
import game.framework.Player;
import game.standard.GameImpl;
import server.ActionPerformer;

public class ActionPerformerStub implements ActionPerformer {
    private GameImpl game;

    @Override
    public void perform(String s, Game game, Player player) throws ReflectiveOperationException {
        System.out.println(s);
    }
}
