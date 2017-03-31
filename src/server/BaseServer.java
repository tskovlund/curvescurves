package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The function of this class is to form a basic server.
 * The server will hold a given number of socket TCP connections to a client application.
 */
public class BaseServer {

    //specify the port of the server
    private int portNumber = 40499;
    private ServerSocket serverSocket;
    private boolean running;

    public BaseServer() {
        this.running = true;
    }

    private void start() {
        registerOnPort();

        while (running) {
            Socket socket = waitForConnectionFromClient();


        }



    }
    private Socket waitForConnectionFromClient () {
        Socket res = null;
        try {
            res = serverSocket.accept();
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
}
