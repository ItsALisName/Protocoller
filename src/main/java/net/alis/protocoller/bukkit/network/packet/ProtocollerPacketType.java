package net.alis.protocoller.bukkit.network.packet;

import net.alis.protocoller.bukkit.util.reflection.NetworkReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketType;

public class ProtocollerPacketType implements MinecraftPacketType {

    private final String packetName;
    private final PacketType.State packetState;
    private final byte packetId;
    private final Class<?> packetClass;

    public ProtocollerPacketType(String packetName, PacketType.State state, byte packetId) {
        this.packetState = state;
        this.packetId = packetId;
        switch (state) {
            case PLAY_IN:this.packetName = "PacketPlayIn" + packetName; break;
            case PLAY_OUT:this.packetName = "PacketPlayOut" + packetName; break;
            case PLAY_OUT_CLIENTBOUND:this.packetName = "Clientbound" + packetName; break;
            case PLAY_IN_SERVERBOUND:this.packetName = "Serverbound" + packetName; break;
            case LOGIN_IN:this.packetName = "PacketLoginIn" + packetName; break;
            case LOGIN_OUT:this.packetName = "PacketLoginOut" + packetName; break;
            case STATUS_IN:this.packetName = "PacketStatusIn" + packetName; break;
            case STATUS_OUT:this.packetName = "PacketStatusOut" + packetName; break;
            case HANDSHAKE_IN:this.packetName = "PacketHandshakingIn" + packetName; break;
            case HANDSHAKE_OUT:this.packetName = "PacketHandshakingOut" + packetName; break;
            default: this.packetName = "UnknownPacket"; break;
        }
        this.packetClass = NetworkReflection.packet$getPacketClass(this.packetName, state);
    }

    public String getPacketName() {
        if(packetName.contains("$")) return packetName.split("\\$")[1];
        return packetName;
    }

    public PacketType.State getState() {
        return packetState;
    }

    public byte getPacketId() {
        return packetId;
    }

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
        return "PacketType{" +
                "packetName='" + packetName + '\'' +
                ", packetState=" + packetState +
                ", packetId=" + packetId +
                ", packetClass=" + packetClass +
                '}';
    }
}
