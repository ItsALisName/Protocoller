package net.alis.protocoller.bukkit.data;

import net.alis.protocoller.ApiUser;

import java.util.HashSet;
import java.util.Set;

public class UsersContainer {

    private final Set<ApiUser> registeredUsers;

    protected UsersContainer() {
        this.registeredUsers = new HashSet<>();
    }

    public Set<ApiUser> getRegisteredUsers() {
        return registeredUsers;
    }


}
