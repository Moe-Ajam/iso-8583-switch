package org.moe.transport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MessageReader {

    public static final int HEADER_LENGTH = 4;

    public static String readMessage(InputStream message) throws IOException {
        String header = new String(message.readNBytes(HEADER_LENGTH), StandardCharsets.UTF_8);
        int messageLength = Integer.parseInt(header);
        return new String(message.readNBytes(messageLength), StandardCharsets.UTF_8);
    }
}
