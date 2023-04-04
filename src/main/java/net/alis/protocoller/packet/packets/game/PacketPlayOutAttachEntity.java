package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutAttachEntity implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int attachedEntityId;
    private int holdingEntityId;

    private final boolean legacyPacket = GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9);

    public PacketPlayOutAttachEntity(PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(legacyPacket) {
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
                        MinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(attachedEntityId)),
                        MinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(holdingEntityId))
                ));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null,
                        MinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(attachedEntityId)),
                        MinecraftReflection.getMinecraftEntity(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(holdingEntityId))
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
        if(legacyPacket) {
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
        if(legacyPacket) {
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
