package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.network.packet.IndexedParam;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.entity.player.PlayerAction;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInEntityAction implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int entityId;
    private PlayerAction action;
    private int mountJumpHeight;

    public PacketPlayInEntityAction(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.action = PlayerAction.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getPlayerActionEnum()).ordinal());
        this.mountJumpHeight = packetData.readInt(1);
    }

    public PacketPlayInEntityAction(int entityId, PlayerAction action, int mountJumpHeight) {
        if(GlobalProvider.instance().getData().getEntitiesContainer().isIdPresent(entityId)) {
            PacketBuilder creator = PacketBuilder.get(getPacketType());
            switch (creator.getConstructorIndicator().getLevel()) {
                case 0: {
                    IndexedParam<?,?>[] params = {
                        new IndexedParam<>(entityId, 0),
                        new IndexedParam<>(action.original(), 0),
                        new IndexedParam<>(mountJumpHeight, 1)
                    };
                    this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                    break;
                }
                case 1: {
                    this.packetData = new PacketDataSerializer(creator.buildPacket(null,
                            MinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(entityId)),
                            action.original(),
                            mountJumpHeight
                    ));
                    break;
                }
                default: {
                    this.packetData = null;
                    break;
                }
            }
            this.entityId = entityId;
            this.action = action;
            this.mountJumpHeight = mountJumpHeight;
            return;
        }
        this.packetData = null;
        ExceptionBuilder.throwException(new RuntimeException("Entity with id " + entityId + " not founded!"), true);
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity getEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.entityId);
    }

    public void setEntityId(int entityId) {
        if(GlobalProvider.instance().getData().getEntitiesContainer().isIdPresent(entityId)) {
            this.packetData.writeInt(0, entityId);
            this.entityId = entityId;
            return;
        }
        LogsManager.get().getLogger().warn("Entity with id " + entityId + " not founded!");
    }

    public PlayerAction getAction() {
        return action;
    }

    public void setAction(PlayerAction action) {
        this.action = action;
    }

    public int getMountJumpHeight() {
        return mountJumpHeight;
    }

    public void setMountJumpHeight(int mountJumpHeight) {
        this.mountJumpHeight = mountJumpHeight;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ENTITY_ACTION;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
