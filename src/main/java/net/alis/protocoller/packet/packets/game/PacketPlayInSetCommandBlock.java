package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.entity.block.TileEntityCommandType;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInSetCommandBlock implements PlayInPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private String command;
    private TileEntityCommandType type;
    private boolean trackOutput;
    private boolean conditional;
    private boolean alwaysActive;

    public PacketPlayInSetCommandBlock(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.command = packetData.readString(0);
        this.type = TileEntityCommandType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getTileEntityCommandTypeEnum()).ordinal());
        this.trackOutput = packetData.readBoolean(0);
        this.conditional = packetData.readBoolean(1);
        this.alwaysActive = packetData.readBoolean(2);
    }

    public PacketPlayInSetCommandBlock(BlockPosition pos, String command, TileEntityCommandType type, boolean trackOutput, boolean conditional, boolean alwaysActive) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(pos.createOriginal(), 0),
                        new IndexedParam<>(command, 0),
                        new IndexedParam<>(type.original(), 0),
                        new IndexedParam<>(trackOutput, 0),
                        new IndexedParam<>(conditional, 1),
                        new IndexedParam<>(alwaysActive, 2)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, pos.createOriginal(), command, type.original(), trackOutput, conditional, alwaysActive));
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

    public void setType(@NotNull TileEntityCommandType type) {
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
