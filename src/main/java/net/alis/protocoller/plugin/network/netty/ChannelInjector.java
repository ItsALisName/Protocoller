package net.alis.protocoller.plugin.network.netty;

import net.alis.protocoller.NetworkPlayer;
import org.bukkit.entity.Player;

public class ChannelInjector {

    public interface PlayerInjector {

        void inject(NetworkPlayer player);

        void eject(NetworkPlayer player);

        boolean isInjected(NetworkPlayer player);

    }

    public interface ServerInjector {

        void inject();

        void eject();

    }

}
