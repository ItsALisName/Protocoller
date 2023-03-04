package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.util.annotations.AddedSince;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import static net.alis.protocoller.bukkit.enums.Version.v1_13;

@AddedSince(v1_13)
public class PacketPlayInSetCommandMinecart implements Packet {

    private final PacketDataSerializer packetData;
    private int entityId;
    private String command;
    private boolean trackOutput;

    public PacketPlayInSetCommandMinecart(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.command = packetData.readString(0);
        this.trackOutput = packetData.readBoolean(0);
    }

    public PacketPlayInSetCommandMinecart(int entityId, String command, boolean trackOutput) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(entityId, 0),
                        new IndexedParam<>(command, 0),
                        new IndexedParam<>(trackOutput, 0)
                };
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, entityId, command, trackOutput));
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
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.entityId);
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
