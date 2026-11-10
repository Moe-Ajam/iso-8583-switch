package org.moe.codec;

public class Codec {

    public static Message readMessage(String messageString) {
        //01810100723C448108E0800016999901000000123300000000000000250011081702150001232002151108281258140710006100001631220000123TERM0001MERCHANT0000001CORNER CAFE              RIYADH       SA682
        Message message = new Message();
        message.setMti(messageString.substring(0, 4));
        message.setBitmap(messageString.substring(4,20));
        return message;
    }
}
