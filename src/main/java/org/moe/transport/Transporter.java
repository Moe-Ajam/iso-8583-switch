package org.moe.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Transporter {
    public static Socket connect(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    public static void writeToStream(OutputStream outputStream, String message) throws IOException {
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
    }

}
