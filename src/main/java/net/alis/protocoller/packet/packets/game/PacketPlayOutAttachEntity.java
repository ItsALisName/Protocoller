package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.enums.Version;
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

public class PacketPlayOutAttachEntity implements Packet {

    private final PacketDataContainer packetData;
    private int attachedEntityId;
    private int holdingEntityId;

    public PacketPlayOutAttachEntity(PacketDataContainer packetData) {
        this.packetData = packetData;
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9)) {
            this.attachedEntityId = packetData.readInt(1);
            this.holdingEntityId = packetData.readInt(2);
        } else {
            this.attachedEntityId = packetData.readInt(0);
            this.holdingEntityId = packetData.readInt(1);
        }
    }

    public PacketPlayOutAttachEntity(int attachedEntityId, int holdingEntityId) {
        PacketBuilder builder = PacketBuilder.get(getPacketType());
        switch (builder.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null,
                        1,
                        AlMinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(attachedEntityId)),
                        AlMinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(holdingEntityId))
                ));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null,
                        AlMinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(attachedEntityId)),
                        AlMinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(holdingEntityId))
                ));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.attachedEntityId = attachedEntityId;
        this.holdingEntityId = holdingEntityId;
    }


    public PacketPlayOutAttachEntity(Entity attachedEntity, Entity holdingEntity) {
        this(attachedEntity.getEntityId(), holdingEntity.getEntityId());
    }

    public int getAttachedEntityId() {
        return attachedEntityId;
    }

    public void setAttachedEntityId(int attachedEntityId) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9)) {
            this.packetData.writeInt(1, attachedEntityId);
        } else {
            this.packetData.writeInt(0, attachedEntityId);
        }
        this.attachedEntityId = attachedEntityId;
    }

    public int getHoldingEntityId() {
        return holdingEntityId;
    }

    public void setHoldingEntityId(int holdingEntityId) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9)) {
            this.packetData.writeInt(2, attachedEntityId);
        } else {
            this.packetData.writeInt(1, attachedEntityId);
        }
        this.holdingEntityId = holdingEntityId;
    }

    @Nullable
    public Entity getAttachedEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.attachedEntityId);
    }

    @Nullable
    public Entity getHoldingEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.holdingEntityId);
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.ATTACH_ENTITY;
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
