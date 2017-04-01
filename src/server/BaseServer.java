package server;

import game.framework.Game;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

/**
 * The function of this class is to form a basic server.
 * The server will hold a given number of socket TCP connections to a client application.
 */
public class BaseServer {

    //specify the port of the server
    private int portNumber = 8080;
    private ServerSocket serverSocket;
    private boolean running;
    private ActionPerformer actionPerformer;
    private Game game;
    private HashMap<Socket, String> socketPlayerHashMap;

    public BaseServer(ActionPerformer actionPerformer, Game game) {
        this.actionPerformer = actionPerformer;
        this.game = game;
        this.running = true;
        socketPlayerHashMap = new HashMap<>();
    }

    public void start() {
        try {
            registerOnPort();
            printLocalHostAddress();
            while (running) {
                //Main thread is waiting for clients
                Socket socket = waitForConnectionFromClient();
                WebSocketHandler handler = new WebSocketHandler(socket);
                if (handler.handshake()) {
                    Thread t = new Thread(() -> {
                        try {
                            String s;
                            while ((s = handler.receiveMessage()) != null) {
                                actionPerformer.perform(s, game, game.getPlayer(socketPlayerHashMap.get(socket)));
                            }
                            System.out.println("End of stream");
                        } catch (IOException e) {
                            System.out.println("Something is wrong");
                            e.printStackTrace();
                        } catch (ReflectiveOperationException e) {
                            e.printStackTrace();
                        }
                    });
                    t.start();
                } else {
                    throw new ConnectException("Handshake failed");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket waitForConnectionFromClient() {
        Socket res = null;
        try {
            res = serverSocket.accept();
            String playerName = String.valueOf(game.getPlayerMap().size());
            game.addPlayer(playerName, game.getAvailableColor());
            socketPlayerHashMap.put(res, playerName);
            System.out.println("Players connected: " + game.getPlayerMap().size());
        } catch (IOException e) {
            System.out.println("The client has failed to connect, when server tried to accept the socket");
        }
        return res;
    }

    protected void registerOnPort() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            serverSocket = null;
            System.err.println("Cannot open server socket on port number" + portNumber);
            System.err.println(e);
            System.exit(-1);
        }
    }

    public Game getGame() {
        return game;
    }

    protected void printLocalHostAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            String localhostAddress = localhost.getHostAddress();
            System.out.println("Contact this server on the IP address " + localhostAddress);
        } catch (UnknownHostException e) {
            System.err.println("Cannot resolve the Internet address of the local host.");
            System.err.println(e);
            System.exit(-1);
        }
    }
}
