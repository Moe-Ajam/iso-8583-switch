package org.moe.simulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalTest {

    @Test
    void framesMessage() {
        String message = "0123456789";
        String framedMessage = Terminal.framer(message);
        assertEquals("00100123456789", framedMessage);
    }
}