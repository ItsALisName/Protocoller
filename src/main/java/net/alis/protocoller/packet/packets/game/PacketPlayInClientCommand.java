package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayInClientCommand implements PlayInPacket {

    private final PacketDataContainer packetData;
    private ClientCommand mode;

    public PacketPlayInClientCommand(@NotNull PacketDataContainer packetData) {

        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.mode = ClientCommand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getClientCommandEnum()).ordinal());
    }

    public PacketPlayInClientCommand(@NotNull ClientCommand mode) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, mode.original()));
        this.mode = mode;
    }

    public ClientCommand getMode() {
        return mode;
    }

    public void setMode(@NotNull ClientCommand mode) {
        this.packetData.writeEnumConstant(0, mode.original());
        this.mode = mode;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.CLIENT_COMMAND;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
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

        @Contract(pure = true)
        public static @Nullable ClientCommand getById(int id) {
            for(ClientCommand s : ClientCommand.values())
                if(s.id == id) return s;
            return null;
        }

        public @NotNull Enum<?> original() {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getClientCommandEnum(), this.id);
        }
    }
}
