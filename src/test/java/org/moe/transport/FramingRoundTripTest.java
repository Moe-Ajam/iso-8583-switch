package org.moe.transport;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FramingRoundTripTest {

    @Test
    void preservesMessage() {
        String message = "CAFÉ";
        String framedMessage = MessageWriter.frame(message);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(framedMessage.getBytes(StandardCharsets.UTF_8));
        try {
            String result = MessageReader.readMessage(inputStream);
            assertEquals("CAFÉ", result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
