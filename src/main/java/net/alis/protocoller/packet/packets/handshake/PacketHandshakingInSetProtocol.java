package net.alis.protocoller.packet.packets.handshake;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.network.ProtocolEnum;

public class PacketHandshakingInSetProtocol implements Packet {

    private final PacketDataSerializer packetData;
    public String address;
    private int protocolVersion;
    public int port;
    public ProtocolEnum intendedState;
    private final boolean isLegacy = GlobalProvider.instance().getServer().isLegacy();

    public PacketHandshakingInSetProtocol(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.address = packetData.readString(0);
        this.intendedState = ProtocolEnum.get(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getProtocolEnum()).ordinal());
        if(isLegacy) {
            this.protocolVersion = packetData.readInt(0);
            this.port = packetData.readInt(1);
        } else {
            this.protocolVersion = packetData.readInt(1);
            this.port = packetData.readInt(2);
        }
    }

    public PacketHandshakingInSetProtocol(String address, int port, ProtocolEnum intendedState) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?, ?>[] params = {
                        new IndexedParam<>(address, 0),
                        new IndexedParam<>(port, 0),
                        new IndexedParam<>(ProtocolEnum.original(intendedState), 0)
                };
                this.packetData = new PacketDataSerializer(converter.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.create(null, address, port, ProtocolEnum.original(intendedState)));
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
        if(isLegacy) {
            this.packetData.writeInt(0, protocolVersion);
        } else {
            this.packetData.writeInt(1, protocolVersion);
        }
        this.protocolVersion = protocolVersion;
    }

    public void setPort(int port) {
        if(isLegacy) {
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
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
