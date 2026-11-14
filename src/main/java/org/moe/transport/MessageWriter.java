package org.moe.transport;

public class MessageWriter {
    public static String frame(String message) {
        int length = message.length();
        String frame = String.format("%4s", length).replace(' ', '0');
        return frame + message;
    }
}
