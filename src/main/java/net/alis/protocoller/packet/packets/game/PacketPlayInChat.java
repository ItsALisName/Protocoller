package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.*;

public class PacketPlayInChat implements Packet {

    private final PacketDataSerializer packetData;
    private String message;

    public PacketPlayInChat(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.message = this.packetData.readString(0);
    }

    public PacketPlayInChat(String message) {
        PacketCreator converter = PacketCreator.get(PacketType.Play.Client.CHAT);
        if(converter.getConstructorIndicator().getLevel() > 0) {
            this.packetData = new PacketDataSerializer(converter.create(null, message));
        } else {
            IndexedParam<?, ?>[] params = {
                    new IndexedParam<>(message, 0)
            };
            this.packetData = new PacketDataSerializer(converter.create(params, (Object) null));
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.packetData.writeString(0, message);
        this.message = message;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.CHAT;
    }

    @Override
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
