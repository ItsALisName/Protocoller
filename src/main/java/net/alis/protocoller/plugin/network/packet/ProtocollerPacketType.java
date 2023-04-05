package net.alis.protocoller.plugin.network.packet;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketType;
import org.jetbrains.annotations.NotNull;

public class ProtocollerPacketType implements MinecraftPacketType {

    private final String packetName;
    private final PacketType.State packetState;
    private final byte packetId;
    private final Class<?> packetClass;

    public ProtocollerPacketType(String packetName, @NotNull PacketType.State state, byte packetId) {
        this.packetState = state;
        this.packetId = packetId;
        switch (state) {
            case PLAY_CLIENTBOUND:this.packetName = "PacketPlayIn" + packetName; break;
            case PLAY_SERVERBOUND:this.packetName = "PacketPlayOut" + packetName; break;
            case CLIENTBOUND:this.packetName = "Clientbound" + packetName; break;
            case SERVERBOUND:this.packetName = "Serverbound" + packetName; break;
            case LOGIN_CLIENTBOUND:this.packetName = "PacketLoginIn" + packetName; break;
            case LOGIN_SERVERBOUND:this.packetName = "PacketLoginOut" + packetName; break;
            case STATUS_CLIENTBOUND:this.packetName = "PacketStatusIn" + packetName; break;
            case STATUS_SERVERBOUND:this.packetName = "PacketStatusOut" + packetName; break;
            case HANDSHAKE_CLIENTBOUND:this.packetName = "PacketHandshakingIn" + packetName; break;
            case HANDSHAKE_SERVERBOUND:this.packetName = "PacketHandshakingOut" + packetName; break;
            default: this.packetName = "UnknownPacket"; break;
        }
        this.packetClass = PacketUtils.packet$getPacketClass(this.packetName, state);
    }

    @Override
    public String getPacketName() {
        if(packetName.contains("$")) return packetName.split("\\$")[1];
        return packetName;
    }

    @Override
    public PacketType.State getState() {
        return packetState;
    }

    @Override
    public byte getPacketId() {
        return packetId;
    }

    @Override
    public Class<?> getPacketClass() {
        return packetClass;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MinecraftPacketType) {
            return ((MinecraftPacketType) obj).getPacketId() == this.packetId;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ProtocollerPacketType{" +
                "packetName='" + packetName + '\'' +
                ", packetState=" + packetState +
                ", packetId=" + packetId +
                ", packetClass=" + packetClass +
                '}';
    }
}
