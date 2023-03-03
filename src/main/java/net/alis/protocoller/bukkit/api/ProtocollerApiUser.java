package net.alis.protocoller.bukkit.api;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.ProtocollerApi;
import net.alis.protocoller.bukkit.providers.ApiProvider;
import org.bukkit.plugin.Plugin;

public class ProtocollerApiUser implements ApiUser {
    private final ApiProvider provider;
    private final String name;
    private final String version;
    private final String[] authors;


    public ProtocollerApiUser(Plugin user) {
        this.name = user.getName();
        this.version = user.getDescription().getVersion();
        this.authors = user.getDescription().getAuthors().toArray(new String[0]);
        this.provider = new ApiProvider(this);
    }


    @Override
    public ProtocollerApi api() {
        return provider;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String[] getAuthors() {
        return authors;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ApiUser && ((ApiUser) obj).getName().equalsIgnoreCase(this.name) && ((ApiUser) obj).getVersion().equalsIgnoreCase(this.version);
    }

    @Override
    public boolean equals(ApiUser user) {
        return user.getName().equalsIgnoreCase(this.name) && user.getVersion().equalsIgnoreCase(this.version);
    }
}
