package net.alis.protocoller.packet.packets.handshake;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.network.packet.IndexedParam;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.HandshakeInPacket;
import net.alis.protocoller.samples.network.ProtocolEnum;
import org.jetbrains.annotations.NotNull;

public class PacketHandshakingInSetProtocol implements HandshakeInPacket {

    private final PacketDataContainer packetData;
    private String address;
    private int protocolVersion;
    private int port;
    private ProtocolEnum intendedState;
    private final boolean legacyPacket = GlobalProvider.instance().getServer().isLegacy();

    public PacketHandshakingInSetProtocol(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), PacketType.Handshake.Client.SET_PROTOCOL);
        this.packetData = packetData;
        this.address = packetData.readString(0);
        this.intendedState = ProtocolEnum.get(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getProtocolEnum()).ordinal());
        if(legacyPacket) {
            this.protocolVersion = packetData.readInt(0);
            this.port = packetData.readInt(1);
        } else {
            this.protocolVersion = packetData.readInt(1);
            this.port = packetData.readInt(2);
        }
    }

    public PacketHandshakingInSetProtocol(String address, int port, ProtocolEnum intendedState) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?, ?>[] params = {
                        new IndexedParam<>(address, 0),
                        new IndexedParam<>(port, 0),
                        new IndexedParam<>(ProtocolEnum.original(intendedState), 0)
                };
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, address, port, ProtocolEnum.original(intendedState)));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.intendedState = intendedState;
        this.address = address;
        this.protocolVersion = GlobalProvider.instance().getServer().getVersion().getProtocol();
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public int getPort() {
        return port;
    }

    public void setAddress(String address) {
        this.packetData.writeString(0, address);
        this.address = address;
    }

    public void setProtocolVersion(int protocolVersion) {
        if(legacyPacket) {
            this.packetData.writeInt(0, protocolVersion);
        } else {
            this.packetData.writeInt(1, protocolVersion);
        }
        this.protocolVersion = protocolVersion;
    }

    public void setPort(int port) {
        if(legacyPacket) {
            this.packetData.writeInt(1, port);
        } else {
            this.packetData.writeInt(2, port);
        }
        this.port = port;
    }

    public ProtocolEnum getIntendedState() {
        return intendedState;
    }

    public void setIntendedState(ProtocolEnum intendedState) {
        this.packetData.writeEnumConstant(0, ProtocolEnum.original(intendedState));
        this.intendedState = intendedState;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Handshake.Client.SET_PROTOCOL;
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
