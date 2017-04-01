package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


    //TODO listen for new clients
    private void start() {
        registerOnPort();

        while (running) {
            //Main thread is waiting for clients
            Socket socket = waitForConnectionFromClient();

            Thread t = new Thread(() -> {
                try {
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String s;
                    while ((s = fromClient.readLine()) != null) {
                        System.out.println("From client" + s);
                    }
                    System.out.println("End of stream");
                } catch (IOException e) {
                    System.out.println("Something is wrong");
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }

    private Socket waitForConnectionFromClient() {
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

    public static void main(String[] args) {
        BaseServer baseServer = new BaseServer();
        baseServer.start();
    }
}
