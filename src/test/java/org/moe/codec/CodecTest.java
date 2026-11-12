package org.moe.codec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodecTest {
    private static final String SAMPLE = "0100723C448108E0800016999901000000123300000000000000250011081702150001232002151108281258140710006100001631220000123TERM0001MERCHANT0000001CORNER CAFE              RIYADH       SA682";

    @Test
    void readsMti() {
        Message message = Codec.readMessage(SAMPLE);
        assertEquals("0100", message.getMti());
    }

    @Test
    void readsBitmap() {
        Message message = Codec.readMessage(SAMPLE);
        assertEquals("723C448108E08000", message.getBitmap());
    }

}