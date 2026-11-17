package org.moe.transport;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageWriterTest {
    @Test
    void framesMessage() {
        String message = "0123456789";
        String framedMessage = MessageWriter.frame(message);
        assertEquals("00100123456789", framedMessage);
    }

    //TODO: Create the round-trip test case and check the bytes will match
    void preservesMessage() {
        String message = "CAFÉ";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String framedMessage = MessageWriter.frame(message);
        try {
            Connection.write(out, framedMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream in;

    }

}