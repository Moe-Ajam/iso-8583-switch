package org.moe.transport;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageFramerTest {

    @Test
    void readsMessageAfterLengthHeader() throws IOException {
        ByteArrayInputStream message = new ByteArrayInputStream("00100123456789".getBytes());
        String framedMessage = MessageReader.readMessage(message);
        assertEquals("0123456789", framedMessage);
    }

}