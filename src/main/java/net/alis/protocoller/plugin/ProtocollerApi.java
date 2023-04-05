package net.alis.protocoller.plugin;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.Protocoller;
import net.alis.protocoller.plugin.providers.ApiProvider;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ProtocollerApi implements Protocoller {

    @Contract("_ -> new")
    public @NotNull ApiUser registerUser(Plugin plugin) {
        return new ApiProvider(plugin);
    }

    @Contract(pure = true)
    public @NotNull String getVersion() {
        return "0.0.1";
    }

    @Override
    public int registeredUsersCount() {
        return GlobalProvider.instance().getData().getUsersContainer().getRegisteredUsers().size();
    }

}
