package net.alis.protocoller.bukkit.network.netty;

import net.alis.protocoller.entity.NetworkPlayer;
import org.bukkit.entity.Player;

public class ChannelInjector {

    public interface PlayerInjector {

        void inject(Player player);

        void eject(Player player);

        void eject(NetworkPlayer player);

        boolean isInjected(Player player);

    }

    public interface ServerInjector {

        void inject();

        void eject();

    }

}
