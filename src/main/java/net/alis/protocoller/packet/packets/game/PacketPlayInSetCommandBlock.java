package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.entity.block.TileEntityCommandType;

public class PacketPlayInSetCommandBlock implements Packet {

    private final PacketDataSerializer packetData;
    private BlockPosition position;
    private String command;
    private TileEntityCommandType type;
    private boolean trackOutput;
    private boolean conditional;
    private boolean alwaysActive;

    public PacketPlayInSetCommandBlock(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.command = packetData.readString(0);
        this.type = TileEntityCommandType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getTileEntityCommandTypeEnum()).ordinal());
        this.trackOutput = packetData.readBoolean(0);
        this.conditional = packetData.readBoolean(1);
        this.alwaysActive = packetData.readBoolean(2);
    }

    public PacketPlayInSetCommandBlock(BlockPosition pos, String command, TileEntityCommandType type, boolean trackOutput, boolean conditional, boolean alwaysActive) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(pos.createOriginal(), 0),
                        new IndexedParam<>(command, 0),
                        new IndexedParam<>(type.original(), 0),
                        new IndexedParam<>(trackOutput, 0),
                        new IndexedParam<>(conditional, 1),
                        new IndexedParam<>(alwaysActive, 2)
                };
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, pos.createOriginal(), command, type.original(), trackOutput, conditional, alwaysActive));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.position = pos;
        this.command = command;
        this.type = type;
        this.trackOutput = trackOutput;
        this.conditional = conditional;
        this.alwaysActive = alwaysActive;
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.packetData.writeString(0, command);
        this.command = command;
    }

    public TileEntityCommandType getType() {
        return type;
    }

    public void setType(TileEntityCommandType type) {
        this.packetData.writeEnumConstant(0, type.original());
        this.type = type;
    }

    public boolean isTrackOutput() {
        return trackOutput;
    }

    public void setTrackOutput(boolean trackOutput) {
        this.packetData.writeBoolean(0, trackOutput);
        this.trackOutput = trackOutput;
    }

    public boolean isConditional() {
        return conditional;
    }

    public void setConditional(boolean conditional) {
        this.packetData.writeBoolean(1, conditional);
        this.conditional = conditional;
    }

    public boolean isAlwaysActive() {
        return alwaysActive;
    }

    public void setAlwaysActive(boolean alwaysActive) {
        this.packetData.writeBoolean(2, alwaysActive);
        this.alwaysActive = alwaysActive;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.SET_COMMAND_BLOCK;
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
