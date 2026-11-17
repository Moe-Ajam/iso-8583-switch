package org.moe.simulator;

import org.moe.transport.MessageWriter;
import org.moe.transport.Transporter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Terminal {
    public static void sendMessage(String message) {
        try {
            Socket socket = Transporter.connect("localhost", 5001);
            OutputStream out = socket.getOutputStream();
            String framedMessage = MessageWriter.frame(message);
            Transporter.writeToStream(out, framedMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}