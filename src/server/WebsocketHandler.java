package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import sun.misc.BASE64Encoder;

/**
 *
 * @author
 * Anders
 */
public class WebsocketHandler {

    public static final int MASK_SIZE = 4;
    private Socket socket;

    public WebsocketHandler(Socket socket) throws IOException {
        this.socket = socket;
    }

    public boolean handshake() throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        HashMap<String, String> keys = new HashMap<>();
        String str;
        //Reading client handshake
        while (!(str = in.readLine()).equals("")) {
            String[] s = str.split(": ");
            System.out.println();
            System.out.println(str);
            if (s.length == 2) {
                keys.put(s[0], s[1]);
            }
        }
        //Do what you want with the keys here, we will just use "Sec-WebSocket-Key"

        String hash;
        try {
            hash = new BASE64Encoder().encode(MessageDigest.getInstance("SHA-1").digest((keys.get("Sec-WebSocket-Key") + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return false;
        }

        //Write handshake response
        out.write("HTTP/1.1 101 Switching Protocols\r\n"
                + "Upgrade: websocket\r\n"
                + "Connection: Upgrade\r\n"
                + "Sec-WebSocket-Accept: " + hash + "\r\n"
                + "\r\n");
        out.flush();

        return true;
    }

    private byte[] readBytes(int numOfBytes) throws IOException {
        byte[] b = new byte[numOfBytes];
        socket.getInputStream().read(b);
        return b;
    }


    public String receiveMessage() throws IOException {
        byte[] buf = readBytes(2);
        int opcode = buf[0] & 0x0F;
        if (opcode == 8) {
            //Client want to close connection!
            System.out.println("Client closed!");
            socket.close();
            System.exit(0);
            return null;
        } else {
            final int payloadSize = getSizeOfPayload(buf[1]);
            buf = readBytes(MASK_SIZE + payloadSize);
            buf = unMask(Arrays.copyOfRange(buf, 0, 4), Arrays.copyOfRange(buf, 4, buf.length));
            return new String(buf);
        }
    }

    private int getSizeOfPayload(byte b) {
        //Must subtract 0x80 from masked frames
        return ((b & 0xFF) - 0x80);
    }

    private byte[] unMask(byte[] mask, byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] ^ mask[i % mask.length]);
        }
        return data;
    }
}