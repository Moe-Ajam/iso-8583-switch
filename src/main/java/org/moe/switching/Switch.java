package org.moe.switching;

import org.moe.transport.MessageReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Switch {
    public static String listen() throws IOException {
        ServerSocket server = new ServerSocket(5001);
        IO.println("waiting...");
        Socket client = server.accept();
        IO.println("Connected from " + client.getInetAddress());
        return MessageReader.readMessage(client.getInputStream());
    }
}
