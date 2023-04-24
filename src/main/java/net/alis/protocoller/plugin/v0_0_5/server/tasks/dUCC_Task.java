package net.alis.protocoller.plugin.v0_0_5.server.tasks;

import net.alis.protocoller.NetworkServer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.v0_0_5.command.ProtocolBukkitCommandMap;

public class dUCC_Task implements Runnable {

    public static void start() {
        ITaskAccess.get().doAsyncTimerTask(new dUCC_Task(), 0, 140L);
    }

    @Override
    public void run() {
        NetworkServer server = IProtocolAccess.get().getServer();
        ((ProtocolBukkitCommandMap)server.getCommandMap()).registerProtocollerDefaults();
        server.syncCommands();
    }
}
