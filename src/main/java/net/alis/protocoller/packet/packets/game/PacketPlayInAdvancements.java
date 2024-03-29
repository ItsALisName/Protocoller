package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.resources.MinecraftKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayInAdvancements implements PlayInPacket {

    private final PacketDataContainer packetData;
    private Status status;
    private @Nullable MinecraftKey minecraftKey;

    public PacketPlayInAdvancements(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.status = Status.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getAdvancementsStatusEnum()).ordinal());
        this.minecraftKey = packetData.readMinecraftKey(0);
    }

    public PacketPlayInAdvancements(Status status, @Nullable MinecraftKey minecraftKey) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getPacketLevel().getLevel()) {
            case 0: {
                int i = 0;
                IndexedParam<?,?>[] params = {new IndexedParam<>(status.original(), 0), new IndexedParam<>(minecraftKey.createOriginal(), 0)};
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, status.original(), minecraftKey.createOriginal()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.status = status;
        this.minecraftKey = minecraftKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull Status status) {
        this.packetData.writeEnumConstant(0, status.original());
        this.status = status;
    }

    @Nullable
    public MinecraftKey getMinecraftKey() {
        return minecraftKey;
    }

    public void setMinecraftKey(MinecraftKey minecraftKey) {
        this.packetData.writeMinecraftKey(0, minecraftKey);
        this.minecraftKey = minecraftKey;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ADVANCEMENTS;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    public enum Status {

        OPENED_TAB(0),
        CLOSED_SCREEN(1);

        private final int id;

        Status(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Contract(pure = true)
        public static @Nullable Status getById(int id) {
            for(Status s : Status.values())
                if(s.id == id) return s;
            return null;
        }

        public @NotNull Enum<?> original() {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getAdvancementsStatusEnum(), this.id);
        }

    }

}
