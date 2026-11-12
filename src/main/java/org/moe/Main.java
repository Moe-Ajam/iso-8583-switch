package org.moe;

import org.moe.codec.Codec;
import org.moe.codec.Message;

public class Main {

    static void main() {
        String SAMPLE = "0100723C448108E0800016999901000000123300000000000000250011081702150001232002151108281258140710006100001631220000123TERM0001MERCHANT0000001CORNER CAFE              RIYADH       SA682";

        Message message = Codec.readMessage(SAMPLE);

        System.out.println(message.getMti());
        System.out.println(message.getBitmap());
    }
}
