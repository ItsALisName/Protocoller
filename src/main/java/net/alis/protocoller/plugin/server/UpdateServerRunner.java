package net.alis.protocoller.plugin.server;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.server.rcon.ProtocollerQueryThreadGS4;
import net.alis.protocoller.plugin.server.rcon.ProtocollerRconThread;
import net.alis.protocoller.plugin.util.TaskSimplifier;

public class UpdateServerRunner implements Runnable {

    public static void start() {
        TaskSimplifier.get().preformAsyncTimerTask(new UpdateServerRunner(), 0, 60L);
    }

    @Override
    public void run() {
        if(GlobalProvider.instance().getServer().getQueryThreadGC4() == null) {
            Object remoteStatusListener = GlobalProvider.instance().getServer().getHandle().read(0, ClassesContainer.get().getRemoteStatusListenerClass());
            Object remoteControlListener = GlobalProvider.instance().getServer().getHandle().read(0, ClassesContainer.get().getRemoteControlListenerClass());
            if(remoteStatusListener != null) GlobalProvider.instance().getServer().setQueryThreadGS4(new ProtocollerQueryThreadGS4(remoteStatusListener));
            if(remoteControlListener != null) GlobalProvider.instance().getServer().setRconThread(new ProtocollerRconThread(remoteControlListener, GlobalProvider.instance().getServer()));
        }
    }
}
