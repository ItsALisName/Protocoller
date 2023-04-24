package net.alis.protocoller.plugin.v0_0_5.netty;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.NetworkServer;

public class ChannelInjector {

    public interface PlayerInjector {

        void inject(NetworkPlayer player);

        void eject(NetworkPlayer player);

        boolean isInjected(NetworkPlayer player);

    }

    public interface ServerInjector {

        void inject(NetworkServer server);

        void eject();

    }

}
