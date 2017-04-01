package server;


import game.framework.Direction;
import game.framework.Game;
import game.framework.Player;

public class ServerToGameAdapter implements ActionPerformer {

    @Override
    public void perform(String s, Game game, Player player) throws NoSuchMethodException {
        Direction direction;

        switch (s) {
            case "right":
                direction = Direction.RIGHT;
                break;
            case "left":
                direction = Direction.LEFT;
                break;
            case "forward":
                direction = Direction.FORWARD;
                break;
            default : throw new NoSuchMethodException("The requested method is not valid in the given game");
        }

        game.setPlayerDirection(direction,player);


    }
}
