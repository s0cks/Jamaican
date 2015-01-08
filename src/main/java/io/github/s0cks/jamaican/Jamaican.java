package io.github.s0cks.jamaican;

import io.github.s0cks.jamaican.command.CommandListener;
import io.github.s0cks.jamaican.module.ExpanderModule;
import io.github.s0cks.jamaican.net.irc.IRCConnection;
import io.github.s0cks.jamaican.net.irc.IRCProfile;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public final class Jamaican{
    public static final Logger logger = LogManager.getLogger(Jamaican.class);
    public static final OptionParser argParser = new OptionParser();

    static
    {
        argParser.accepts("noExpand");
    }

    public static void main(String... args)
    throws Exception{
        logger.info("*** Starting Jamaican ***");
        IRCConnection connection = new IRCConnection();

        OptionSet options = argParser.parse(args);
        if(options.has("noExpand")){
            logger.info("Skipping Expander Module");
        } else{
            logger.info("Adding Expander Module");
            connection.EVENT_BUS.register(ExpanderModule.instance());
        }

        connection.EVENT_BUS.register(CommandListener.instance());
        connection.connect(new InetSocketAddress("ipv6.esper.net", 6667), new IRCProfile("Jamaican", "Jamaican", "Jamaican"));
        TimeUnit.SECONDS.sleep(10);
        connection.join("#iWin");
        while(connection.isConnected()){
            TimeUnit.DAYS.sleep(1);
        }
    }
}