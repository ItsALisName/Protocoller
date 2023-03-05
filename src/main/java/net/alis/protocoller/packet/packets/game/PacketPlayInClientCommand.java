package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInClientCommand implements Packet {

    private final PacketDataContainer packetData;
    private ClientCommand mode;

    public PacketPlayInClientCommand(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.mode = ClientCommand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getClientCommandEnum()).ordinal());
    }

    public PacketPlayInClientCommand(ClientCommand mode) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, mode.original()));
        this.mode = mode;
    }

    public ClientCommand getMode() {
        return mode;
    }

    public void setMode(ClientCommand mode) {
        this.packetData.writeEnumConstant(0, mode.original());
        this.mode = mode;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.CLIENT_COMMAND;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }

    public enum ClientCommand {
        PERFORM_RESPAWN(0),
        REQUEST_STATS(1),
        OPEN_INVENTORY_ACHIEVEMENT(2);

        private final int id;

        ClientCommand(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static ClientCommand getById(int id) {
            for(ClientCommand s : ClientCommand.values())
                if(s.id == id) return s;
            return null;
        }

        public Enum<?> original() {
            return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getClientCommandEnum(), this.id);
        }
    }
}
