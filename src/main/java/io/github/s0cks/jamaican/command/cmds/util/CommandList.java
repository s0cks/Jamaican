package io.github.s0cks.jamaican.command.cmds.util;

import io.github.s0cks.jamaican.command.Command;
import io.github.s0cks.jamaican.command.CommandListener;
import io.github.s0cks.jamaican.net.irc.IRCConnection;

import java.util.List;

public final class CommandList
implements Command{
    @Override
    public String getName(){
        return "list|ls";
    }

    @Override
    public String getExample(){
        return ">list";
    }

    @Override
    public String getHelp(){
        return "lists out the commands registered in the bot";
    }

    @Override
    public void invoke(IRCConnection connection, String sender, String channel, String... args){
        List<Command> commands = CommandListener.commands();
        connection.notice(sender, join(commands));
    }

    private String join(List<Command> commands){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < commands.size(); i++){
            builder.append(commands.get(i).getName());
            if(i < commands.size() - 1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}