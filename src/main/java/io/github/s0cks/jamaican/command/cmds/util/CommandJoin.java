package io.github.s0cks.jamaican.command.cmds.util;

import io.github.s0cks.jamaican.command.Command;
import io.github.s0cks.jamaican.net.irc.IRCConnection;

public final class CommandJoin
implements Command{
    @Override
    public String getName(){
        return "j|join";
    }

    @Override
    public String getExample(){
        return ">j #iWin";
    }

    @Override
    public String getHelp(){
        return ">j [channel]";
    }

    @Override
    public void invoke(IRCConnection connection, String sender, String channel, String... args){
        connection.join(args[0]);
    }
}