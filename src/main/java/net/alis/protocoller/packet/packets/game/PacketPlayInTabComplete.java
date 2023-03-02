package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInTabComplete implements Packet {

    private final PacketDataSerializer packetData;
    private int completionId;
    private String partialCommand;

    private final boolean isVeryLegacy = GlobalProvider.instance().getServer().isVeryLegacy();

    public PacketPlayInTabComplete(PacketDataSerializer packetData) {
        this.packetData = packetData;
        if (isVeryLegacy) {
            this.completionId = 0;
        } else {
            this.completionId = packetData.readInt(0);
        }
        this.partialCommand = packetData.readString(0);
    }

    public PacketPlayInTabComplete(int completionId, String partialCommand) {
        PacketCreator converter = PacketCreator.get(PacketType.Play.Client.TAB_COMPLETE);
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?, ?>[] params;
                if(isVeryLegacy) {
                    params = new IndexedParam<?, ?>[]{new IndexedParam<>(partialCommand, 0), new IndexedParam<>(false, 0)};
                } else {
                    params = new IndexedParam<?, ?>[]{new IndexedParam<>(completionId, 0), new IndexedParam<>(partialCommand, 0)};
                }
                this.packetData = new PacketDataSerializer(converter.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.create(null, partialCommand));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.create(null, completionId, partialCommand));
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
        if(!isVeryLegacy) {
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
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
