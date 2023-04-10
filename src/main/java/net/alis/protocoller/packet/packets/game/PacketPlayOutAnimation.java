package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutAnimation implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int entityId;
    private int animationId;

    private final boolean legacyPacket = GlobalProvider.get().getServer().getVersion().lessThan(Version.v1_17);

    public PacketPlayOutAnimation(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(legacyPacket){
            this.entityId = packetData.readInt(0);
            this.animationId = packetData.readInt(1);
        } else {
            this.entityId = packetData.readInt(6);
            this.animationId = packetData.readInt(7);
        }
    }

    public PacketPlayOutAnimation(int entityId, int animationId) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, MinecraftReflection.getMinecraftEntity(GlobalProvider.get().getServer().getEntityList().getEntity(entityId)), animationId));
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
        if(GlobalProvider.get().getServer().getEntityList().isIdPresent(entityId)) {
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
        return GlobalProvider.get().getServer().getEntityList().getEntity(this.entityId);
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.ANIMATION;
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
