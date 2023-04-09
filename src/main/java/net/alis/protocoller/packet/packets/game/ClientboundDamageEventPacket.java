package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.phys.Vector3D;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ClientboundDamageEventPacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private int entityId;
    private int sourceTypeId;
    private int sourceCauseId;
    private int sourceDirectId;
    private Optional<Vector3D> sourcePosition;

    public ClientboundDamageEventPacket(@NotNull PacketDataContainer packetData) {
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.sourceTypeId = packetData.readInt(1);
        this.sourceCauseId = packetData.readInt(2);
        this.sourceDirectId = packetData.readInt(3);
        this.sourcePosition = Optional.of(new Vector3D(((Optional<Optional>)packetData.readOptional(0)).get()));
    }

    public ClientboundDamageEventPacket(int entityId, int sourceTypeId, int sourceCauseId, int sourceDirectId, @NotNull Optional<Vector3D> sourcePosition) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, entityId, sourceTypeId, sourceCauseId, sourceDirectId, Optional.of(sourcePosition.get().createOriginal())));
        this.entityId = entityId;
        this.sourceTypeId = sourceTypeId;
        this.sourceCauseId = sourceCauseId;
        this.sourceDirectId = sourceDirectId;
        this.sourcePosition = sourcePosition;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.packetData = new ClientboundDamageEventPacket(entityId, sourceTypeId, sourceCauseId, sourceDirectId, sourcePosition).packetData;
        this.entityId = entityId;
    }

    public int getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(int sourceTypeId) {
        this.packetData = new ClientboundDamageEventPacket(entityId, sourceTypeId, sourceCauseId, sourceDirectId, sourcePosition).packetData;
        this.sourceTypeId = sourceTypeId;
    }

    public int getSourceCauseId() {
        return sourceCauseId;
    }

    public void setSourceCauseId(int sourceCauseId) {
        this.packetData = new ClientboundDamageEventPacket(entityId, sourceTypeId, sourceCauseId, sourceDirectId, sourcePosition).packetData;
        this.sourceCauseId = sourceCauseId;
    }

    public int getSourceDirectId() {
        return sourceDirectId;
    }

    public void setSourceDirectId(int sourceDirectId) {
        this.packetData = new ClientboundDamageEventPacket(entityId, sourceTypeId, sourceCauseId, sourceDirectId, sourcePosition).packetData;
        this.sourceDirectId = sourceDirectId;
    }

    public Optional<Vector3D> getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(Optional<Vector3D> sourcePosition) {
        this.packetData = new ClientboundDamageEventPacket(entityId, sourceTypeId, sourceCauseId, sourceDirectId, sourcePosition).packetData;
        this.sourcePosition = sourcePosition;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.DAMAGE_EVENT_PACKET;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
