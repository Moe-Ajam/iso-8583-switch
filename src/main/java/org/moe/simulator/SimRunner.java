package org.moe.simulator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SimRunner {
    static void main() {
        String MESSAGE = "0100723C448108E0800016999901000000123300000000000000250011081702150001232002151108281258140710006100001631220000123TERM0001MERCHANT0000001CORNER CAFE              RIYADH       SA682";
        try {
            Socket socket = Terminal.connect(5001);
            OutputStream out = socket.getOutputStream();
            String framedMessage = Terminal.framer(MESSAGE);
            Terminal.writer(out, framedMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
