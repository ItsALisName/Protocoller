package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.AlMinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutAnimation implements Packet {

    private final PacketDataContainer packetData;
    private int entityId;
    private int animationId;

    public PacketPlayOutAnimation(PacketDataContainer packetData) {
        this.packetData = packetData;
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_17)){
            this.entityId = packetData.readInt(0);
            this.animationId = packetData.readInt(1);
        } else {
            this.entityId = packetData.readInt(6);
            this.animationId = packetData.readInt(7);
        }
    }

    public PacketPlayOutAnimation(int entityId, int animationId) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, AlMinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(entityId)), animationId));
        this.entityId = entityId;
        this.animationId = animationId;
    }

    public PacketPlayOutAnimation(Entity entity, int animationId) {
        this(entity.getEntityId(),animationId);
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        if(GlobalProvider.instance().getData().getEntitiesContainer().isIdPresent(entityId)) {
            this.packetData.writeInt(0, entityId);
            this.entityId = entityId;
        }
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.packetData.writeInt(1, animationId);
        this.animationId = animationId;
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.entityId);
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.ANIMATION;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
