package org.moe;

import org.moe.codec.Codec;
import org.moe.codec.Message;
import org.moe.switching.Switch;

import java.io.IOException;

public class Main {

    static void main() {
        try {
            String messageString = Switch.listen();
            Message message = Codec.readMessage(messageString);
            IO.println("MTI: " + message.getMti());
            IO.println("Done");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
