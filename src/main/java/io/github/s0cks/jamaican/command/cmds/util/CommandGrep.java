package io.github.s0cks.jamaican.command.cmds.util;

import io.github.s0cks.jamaican.command.Command;
import io.github.s0cks.jamaican.net.GrepCode;
import io.github.s0cks.jamaican.net.irc.IRCConnection;

import java.io.IOException;

public final class CommandGrep
implements Command{
    @Override
    public String getName(){
        return "grepcode|grep";
    }

    @Override
    public String getHelp(){
        return ">grep [gradle style dependency] [classpath]";
    }

    @Override
    public void invoke(IRCConnection connection, String sender, String channel, String... args){
        String[] dep = args[0].split(":");
        try{
            connection.notice(sender, GrepCode.grep(dep[0], dep[1], dep[2], args[1]));
        } catch(IOException e){
            connection.notice(sender, "Error Grepping: " + args[0] + " - " + args[1]);
        }
    }
}