package game.local;

import game.framework.Canvas;
import game.framework.GameState;
import game.framework.Player;
import game.framework.Position;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Made by Rasmus on 31/03/2017.
 */
public class CanvasImpl extends Application implements Canvas {
    private GraphicsContext gc;

    @Override
    public void update(GameState gs) {
        for (Player player : gs.getPlayers()) {
            drawPlayer(player.getPosition(), player.getColor());
        }
    }

    private void drawPlayer(Position position, Color color) {
        gc.setFill(color);
        gc.fillOval(position.getX(), position.getY(), 10, 10);
    }





    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(300, 250);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        new Thread(() -> update(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Player> players = new ArrayList<>();
            players.add(new Player() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public int getScore() {
                    return 0;
                }

                @Override
                public Position getPosition() {
                    return new Position() {
                        @Override
                        public int getX() {
                            return 10;
                        }

                        @Override
                        public int getY() {
                            return 20;
                        }
                    };
                }

                @Override
                public Color getColor() {
                    return Color.AQUA;
                }
            });
            players.add(new Player() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public int getScore() {
                    return 0;
                }

                @Override
                public Position getPosition() {
                    return new Position() {
                        @Override
                        public int getX() {
                            return 70;
                        }

                        @Override
                        public int getY() {
                            return 100;
                        }
                    };
                }

                @Override
                public Color getColor() {
                    return Color.BEIGE;
                }
            });
            return players;
        })).start();
    }
}
