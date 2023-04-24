package net.alis.protocoller.plugin.v0_0_5.command;

import net.alis.protocoller.NetworkServer;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.samples.command.CommandListenerWrapper;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.samples.phys.Vector2F;
import net.alis.protocoller.samples.phys.Vector3D;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.Nullable;

public class ProtocolCommandListenerWrapper implements CommandListenerWrapper {

    private final AccessedObject handle;
    private final ProtocolWorldServer world;
    private final ProtocolCommandListener base;

    public ProtocolCommandListenerWrapper(Object commandListenerWrapper, @Nullable ProtocolWorldServer world) {
        this.handle = new AccessedObject(commandListenerWrapper, true);
        if(world != null){
            this.world = world;
        } else {
            this.world = new ProtocolWorldServer(this.handle.readField(0, ClassAccessor.get().getMinecraftWorldServerClass()), IProtocolAccess.get().getServer());
        }
        this.base = new ProtocolCommandListener(this.handle.readField(0, ProtocolCommandListener.clazz()));
    }

    public ProtocolCommandListener getBase() {
        return base;
    }

    @Override
    public NetworkServer getServer() {
        return world.getServer();
    }

    public Vector3D getWorldPosition() {
        return new Vector3D(this.handle.readField(0, Vector3D.clazz()));
    }

    public ProtocolWorldServer getWorld() {
        return world;
    }

    public int getPermissionLevel() {
        return this.handle.readField(0, int.class);
    }

    public String getTextName() {
        return this.handle.readField(0, String.class);
    }

    public ChatComponent getDisplayName() {
        return new ChatComponent(ChatSerializer.fromComponent(this.handle.readField(0, ChatComponent.clazz())));
    }

    public boolean isSilent() {
        return this.handle.readField(0, boolean.class);
    }

    public Vector2F getRotation() {
        return new Vector2F(this.handle.readField(0, Vector2F.clazz()));
    }

    public AccessedObject getHandle() {
        return handle;
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getCommandListenerWrapperClass();
    }
}
