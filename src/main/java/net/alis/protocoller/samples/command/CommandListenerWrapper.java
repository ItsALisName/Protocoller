package net.alis.protocoller.samples.command;

import net.alis.protocoller.NetworkServer;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.phys.Vector2F;
import net.alis.protocoller.samples.phys.Vector3D;
import net.alis.protocoller.samples.server.world.level.WorldServer;

public interface CommandListenerWrapper {

    CommandListener getBase();

    Vector3D getWorldPosition();

    NetworkServer getServer();

    WorldServer getWorld();

    int getPermissionLevel();

    String getTextName();

    ChatComponent getDisplayName();

    boolean isSilent();

    Vector2F getRotation();


}
