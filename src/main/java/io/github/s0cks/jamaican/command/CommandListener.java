package io.github.s0cks.jamaican.command;

import io.github.s0cks.jamaican.event.IRCEvent.MessageEvent;
import io.github.s0cks.jamaican.thread.CommandLoaderThread;
import io.github.s0cks.jamaican.thread.CommandSearchThread;
import io.github.s0cks.jamaican.util.Utilities;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CommandListener{
    private static final List<Command> commands = new LinkedList<>();
    private static final ExecutorService runner = Executors.newCachedThreadPool();
    private static CommandListener instance;

    static
    {
        load();
    }

    public static CommandListener instance(){
        return instance == null ? instance = new CommandListener() : instance;
    }

    public static List<Command> commands(){
        return ImmutableList.copyOf(commands);
    }

    public static Command findByName(String name){
        try{
            Future<Command> commandFuture = runner.submit(new CommandSearchThread(commands, name));
            return commandFuture.get();
        } catch(Exception e){
            return null;
        }
    }

    private static void load(){
        try{
            commands.addAll(runner.submit(new CommandLoaderThread()).get());
        } catch(InterruptedException | ExecutionException e){
            e.printStackTrace(System.err);
        }
    }

    @Subscribe
    public void onMessage(MessageEvent e){
        try{
            if(e.message().startsWith(">")){
                String cmd = e.message().split(" ")[0].substring(1);
                Future<Command> commandFuture = runner.submit(new CommandSearchThread(commands, cmd));
                Command command = commandFuture.get();
                if(command != null){
                    command.invoke(e.connection(), e.sender(), e.channel(), Utilities.remove(e.message().split(" "), 0));
                }
            }
        } catch(Exception ex){
            ex.printStackTrace(System.err);
        }
    }
}