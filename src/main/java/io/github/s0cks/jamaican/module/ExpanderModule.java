package io.github.s0cks.jamaican.module;

import io.github.s0cks.jamaican.event.IRCEvent.MessageEvent;
import io.github.s0cks.jamaican.net.URLShortener;
import io.github.s0cks.jamaican.net.irc.IRCConnection;
import io.github.s0cks.jamaican.util.IRCUtils;

import com.google.common.eventbus.Subscribe;

import java.util.regex.Pattern;

public final class ExpanderModule{
    private static final Pattern GOOGL_PATTERN = Pattern.compile("(http://|https://)goo.gl/\\w+");

    private static ExpanderModule instance;

    public static ExpanderModule instance(){
        return instance == null ? instance = new ExpanderModule() : instance;
    }

    public static void expand(IRCConnection connection, String sender, String channel, String... args)
    throws Exception{
        for(String str : args){
            if(GOOGL_PATTERN.matcher(str).matches()){
                connection.message(IRCUtils.getEffectiveTarget(sender, channel), URLShortener.expand(str));
            }
        }
    }

    @Subscribe
    public void onMessage(MessageEvent event){
        try{
            expand(event.connection(), event.sender(), event.channel(), event.message().split(" "));
        } catch(Exception e){
            // Fallthrough
        }
    }
}