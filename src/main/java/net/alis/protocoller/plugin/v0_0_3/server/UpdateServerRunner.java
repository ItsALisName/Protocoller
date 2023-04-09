package net.alis.protocoller.plugin.v0_0_3.server;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.v0_0_3.server.rcon.ProtocolQueryThreadGS4;
import net.alis.protocoller.plugin.v0_0_3.server.rcon.ProtocolRconThread;
import net.alis.protocoller.plugin.util.TaskSimplifier;

public class UpdateServerRunner implements Runnable {

    public static void start() {
        TaskSimplifier.get().preformAsyncTimerTask(new UpdateServerRunner(), 0, 60L);
    }

    @Override
    public void run() {
        if(GlobalProvider.get().getServer().getQueryThreadGC4() == null) {
            Object remoteStatusListener = GlobalProvider.get().getServer().getHandle().read(0, ClassAccessor.get().getRemoteStatusListenerClass());
            Object remoteControlListener = GlobalProvider.get().getServer().getHandle().read(0, ClassAccessor.get().getRemoteControlListenerClass());
            if(remoteStatusListener != null) GlobalProvider.get().getServer().setQueryThreadGS4(new ProtocolQueryThreadGS4(remoteStatusListener));
            if(remoteControlListener != null) GlobalProvider.get().getServer().setRconThread(new ProtocolRconThread(remoteControlListener, GlobalProvider.get().getServer()));
        }
    }
}
