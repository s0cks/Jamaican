package io.github.s0cks.jamaican.command;

import io.github.s0cks.jamaican.net.irc.IRCConnection;

public interface Command{
    public String getName();
    public String getExample();
    public String getHelp();
    public void invoke(IRCConnection connection, String sender, String channel, String... args);
}