package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInTabComplete implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int completionId;
    private String partialCommand;

    private final boolean legacyPacket = IProtocolAccess.get().getServer().isVeryLegacy();

    public PacketPlayInTabComplete(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if (legacyPacket) {
            this.completionId = 0;
        } else {
            this.completionId = packetData.readInt(0);
        }
        this.partialCommand = packetData.readString(0);
    }

    public PacketPlayInTabComplete(int completionId, String partialCommand) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder converter = PacketBuilder.get(PacketType.Play.Client.TAB_COMPLETE);
        switch (converter.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?, ?>[] params;
                if(legacyPacket) {
                    params = new IndexedParam<?, ?>[]{new IndexedParam<>(partialCommand, 0), new IndexedParam<>(false, 0)};
                } else {
                    params = new IndexedParam<?, ?>[]{new IndexedParam<>(completionId, 0), new IndexedParam<>(partialCommand, 0)};
                }
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, partialCommand));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, completionId, partialCommand));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.completionId = completionId;
        this.partialCommand = partialCommand;
    }

    public int getCompletionId() {
        return completionId;
    }

    public void setCompletionId(int completionId) {
        if(!legacyPacket) {
            this.packetData.writeInt(0, completionId);
        }
        this.completionId = completionId;
    }

    public String getPartialCommand() {
        return this.partialCommand;
    }

    public void setPartialCommand(String partialCommand) {
        this.packetData.writeString(0, partialCommand);
        this.partialCommand = partialCommand;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.TAB_COMPLETE;
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
