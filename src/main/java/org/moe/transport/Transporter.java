package org.moe.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {
    public static Socket connect(int port) throws IOException {
        Socket socket = new Socket("localhost", port);
        return socket;
    }


    public static void write(OutputStream out, String message) throws IOException {
        out.write(message.getBytes(StandardCharsets.UTF_8));
    }

}
