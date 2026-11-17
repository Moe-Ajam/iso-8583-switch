package org.moe.transport;

import java.nio.charset.StandardCharsets;

public class MessageWriter {
    public static String frame(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        String frame = String.format("%4s", length).replace(' ', '0');
        return frame + message;
    }
}
