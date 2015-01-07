package io.github.s0cks.jamaican.util;

public final class IRCUtils{
    public static String getEffectiveTarget(String sender, String channel){
        return channel == null ? sender : channel;
    }
}