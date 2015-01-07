package io.github.s0cks.jamaican.thread;

import io.github.s0cks.jamaican.command.Command;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public final class CommandLoaderThread
implements Callable<List<Command>>{
    @Override
    @SuppressWarnings("unchecked")
    public List<Command> call()
    throws Exception{
        ImmutableSet<ClassInfo> classPath = ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClassesRecursive("io.github.s0cks.jamaican.command.cmds");
        List<Command> commands = new LinkedList<>();
        for(ClassInfo info : classPath){
            Command c = createInstance((Class<Command>) info.load());
            commands.add(c);
        }
        return commands;
    }

    private <T> T createInstance(Class<T> tClass)
    throws Exception{
        Constructor<T> tConstructor = tClass.getDeclaredConstructor();
        tConstructor.setAccessible(true);
        return tConstructor.newInstance();
    }
}