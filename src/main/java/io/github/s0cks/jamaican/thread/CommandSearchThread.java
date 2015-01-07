package io.github.s0cks.jamaican.thread;

import io.github.s0cks.jamaican.command.Command;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class CommandSearchThread
implements Callable<Command>{
    private final List<Command> basis;
    private final String target;

    public CommandSearchThread(List<Command> basis, String target){
        this.basis = basis;
        this.target = target;
    }

    @Override
    public Command call()
    throws Exception{
        for(Command cmd : this.basis){
            if(Pattern.compile(cmd.getName()).matcher(target).find()){
                return cmd;
            }
        }

        return null;
    }
}