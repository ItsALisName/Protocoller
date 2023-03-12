package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.*;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInChat implements PlayInPacket {

    private final PacketDataContainer packetData;
    private String message;

    public PacketPlayInChat(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.message = this.packetData.readString(0);
    }

    public PacketPlayInChat(String message) {
        PacketBuilder converter = PacketBuilder.get(PacketType.Play.Client.CHAT);
        if(converter.getConstructorIndicator().getLevel() > 0) {
            this.packetData = new PacketDataSerializer(converter.buildPacket(null, message));
        } else {
            IndexedParam<?, ?>[] params = {
                    new IndexedParam<>(message, 0)
            };
            this.packetData = new PacketDataSerializer(converter.buildPacket(params, (Object) null));
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
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
