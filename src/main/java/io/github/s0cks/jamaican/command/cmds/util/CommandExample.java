package io.github.s0cks.jamaican.command.cmds.util;

import io.github.s0cks.jamaican.command.Command;
import io.github.s0cks.jamaican.command.CommandListener;
import io.github.s0cks.jamaican.net.irc.IRCConnection;

public final class CommandExample
implements Command{
    @Override
    public String getName(){
        return "example|ex";
    }

    @Override
    public String getExample(){
        return ">ex grepcode";
    }

    @Override
    public String getHelp(){
        return ">example [command name]";
    }

    @Override
    public void invoke(IRCConnection connection, String sender, String channel, String... args){
        Command cmd = CommandListener.findByName(args[0]);
        if(cmd != null){
            connection.notice(sender, cmd.getExample());
        }
    }
}