package io.github.s0cks.jamaican.command.cmds.util;

import io.github.s0cks.jamaican.command.Command;
import io.github.s0cks.jamaican.net.irc.IRCConnection;

public final class CommandQuote
implements Command{
    @Override
    public String getName(){
        return "quote|q";
    }

    @Override
    public String getExample(){
        return ">quote NICK Jamaican";
    }

    @Override
    public String getHelp(){
        return ">quote [raw line to send to server]";
    }

    @Override
    public void invoke(IRCConnection connection, String sender, String channel, String... args){
        String line = join(args);
        connection.writeRaw(line);
    }

    private String join(String... args){
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < args.length; i++){
            builder.append(args[1]);
            if(i < args.length - 1){
                builder.append(" ");
            }
        }

        return builder.toString();
    }
}