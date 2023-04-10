package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayInSetCommandMinecart implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int entityId;
    private String command;
    private boolean trackOutput;

    public PacketPlayInSetCommandMinecart(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.command = packetData.readString(0);
        this.trackOutput = packetData.readBoolean(0);
    }

    public PacketPlayInSetCommandMinecart(int entityId, String command, boolean trackOutput) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(entityId, 0),
                        new IndexedParam<>(command, 0),
                        new IndexedParam<>(trackOutput, 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, entityId, command, trackOutput));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.entityId = entityId;
        this.command = command;
        this.trackOutput = trackOutput;
    }

    public int getEntityId() {
        return entityId;
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.get().getServer().getEntityList().getEntity(this.entityId);
    }

    public void setEntityId(int entityId) {
        this.packetData.writeInt(0, entityId);
        this.entityId = entityId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.packetData.writeString(0, command);
        this.command = command;
    }

    public boolean isTrackOutput() {
        return trackOutput;
    }

    public void setTrackOutput(boolean trackOutput) {
        this.packetData.writeBoolean(0, trackOutput);
        this.trackOutput = trackOutput;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.SET_COMMAND_MINECART;
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
