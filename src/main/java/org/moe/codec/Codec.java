package org.moe.codec;

public class Codec {

    public static Message decodeMessage(String messageString) {
        Message message = new Message();
        message.setMti(messageString.substring(0, 4));
        message.setBitmap(messageString.substring(4,20));
        return message;
    }
}
