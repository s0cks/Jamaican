package io.github.s0cks.jamaican.thread;

import io.github.s0cks.jamaican.Jamaican;
import io.github.s0cks.jamaican.event.IRCEvent.ChannelMessageEvent;
import io.github.s0cks.jamaican.event.IRCEvent.JoinChannelEvent;
import io.github.s0cks.jamaican.event.IRCEvent.OnConnectEvent;
import io.github.s0cks.jamaican.event.IRCEvent.PartChannelEvent;
import io.github.s0cks.jamaican.event.IRCEvent.PingEvent;
import io.github.s0cks.jamaican.event.IRCEvent.PrivateMessageEvent;
import io.github.s0cks.jamaican.net.irc.IRCConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public final class ReadThread
        implements Runnable{
    private final Socket socket;
    private final IRCConnection connection;

    public ReadThread(IRCConnection connection, Socket socket){
        this.connection = connection;
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String line;
            while((line = reader.readLine()) != null){
                this.parseLine(line);

                int firstSpace = line.indexOf(" ");
                int secondSpace = line.indexOf(" ", firstSpace + 1);
                if(secondSpace >= 0){
                    String code = line.substring(firstSpace + 1, secondSpace);

                    if(code.equals("004")){
                        this.connection.EVENT_BUS.post(new OnConnectEvent(this.connection));
                    } else if(code.equals("433")){
                        Jamaican.logger.error("Nick already taken");
                        System.exit(-1);
                    } else if(code.startsWith("5") || code.startsWith("4")){
                        Jamaican.logger.error("Cannot log into IRC Server");
                        System.exit(-1);
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace(System.err);
        }
    }

    private void parseLine(String line){
        if(line.startsWith("PING ")){
            this.connection.EVENT_BUS.post(new PingEvent(this.connection, line.substring(5)));
            this.connection.writeRaw(line.replace("PING", "PONG"));
            return;
        }
        Jamaican.logger.info(line);

        String[] tokens = line.split(" ");
        String nick = "";
        String info = tokens[0];
        String command = tokens[1];
        String target = null;
        int excl = info.indexOf('!');
        int at = info.indexOf('@');

        if(info.startsWith(":")){
            if(excl > 0 && at > 0 && excl < at){
                nick = info.substring(1, excl);
            } else{
                int code = -1;
                try{
                    code = Integer.parseInt(command);
                } catch(Exception e){
                    // Ignore
                }

                if(code != -1){
                    // Ignore
                } else{
                    nick = info;
                    target = command;
                }
            }
        }

        command = command.toUpperCase();
        if(nick.startsWith(":")){
            nick = nick.substring(1);
        }

        if(target == null){
            target = tokens[2];
        }

        if(target.startsWith(":")){
            target = target.substring(1);
        }

        if(command.equals("PRIVMSG") && target.charAt(0) == '#'){
            this.connection.EVENT_BUS.post(new ChannelMessageEvent(this.connection, target, nick, line.substring(line.indexOf(" :") + 2)));
        } else if(command.equals("PRIVMSG")){
            this.connection.EVENT_BUS.post(new PrivateMessageEvent(this.connection, nick, line.substring(line.indexOf(" :") + 2)));
        } else if(command.equals("JOIN")){
            this.connection.EVENT_BUS.post(new JoinChannelEvent(this.connection, nick, target));
        } else if(command.equals("PART")){
            this.connection.EVENT_BUS.post(new PartChannelEvent(this.connection, nick, target));
        }
    }
}