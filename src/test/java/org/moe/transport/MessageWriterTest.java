package org.moe.transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageWriterTest {
    @Test
    void framesMessage() {
        String message = "0123456789";
        String framedMessage = MessageWriter.framer(message);
        assertEquals("00100123456789", framedMessage);
    }

}