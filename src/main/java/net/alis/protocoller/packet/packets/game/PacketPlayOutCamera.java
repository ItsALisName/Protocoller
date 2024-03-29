package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutCamera implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int entityId;

    public PacketPlayOutCamera(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
    }

    public PacketPlayOutCamera(Entity entity) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, MinecraftReflection.getMinecraftEntity(entity)));
        this.entityId = entity.getEntityId();
    }

    @Nullable
    public Entity getEntity() {
        return IProtocolAccess.get().getServer().getEntityList().getEntity(this.entityId);
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        if(IProtocolAccess.get().getServer().getEntityList().isIdPresent(entityId)) {
            this.packetData.writeInt(0, entityId);
            this.entityId = entityId;
        }
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.CAMERA;
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
