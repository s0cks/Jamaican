package io.github.s0cks.jamaican.net.irc;

public final class IRCProfile{
    public final String nick;
    public final String username;
    public final String realname;

    public IRCProfile(String nick, String username, String realname){
        this.nick = nick;
        this.username = username;
        this.realname = realname;
    }
}