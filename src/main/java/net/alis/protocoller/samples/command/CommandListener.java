package net.alis.protocoller.samples.command;

import net.alis.protocoller.samples.network.chat.ChatComponent;

import java.util.UUID;

public interface CommandListener {

    void sendSystemMessage(ChatComponent msg, UUID uuid);

    boolean acceptsSuccess();

    boolean acceptsFailure();

    boolean shouldInformAdmins();

    default boolean alwaysAccepts() {
        return false;
    }

}
