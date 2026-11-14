package org.moe.simulator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Terminal {
    public static Socket connect(int port) throws IOException {
        Socket socket = new Socket("localhost", port);
        return socket;
    }

    public static String framer(String message) {
        int length = message.length();
        String frame = String.format("%4s", length).replace(' ', '0');
        return frame + message;
    }

    public static void writer(OutputStream out, String message) throws IOException {
        out.write(message.getBytes(StandardCharsets.UTF_8));
    }
}