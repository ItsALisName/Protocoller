package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInResourcePackStatus implements PlayInPacket {

    private final PacketDataContainer packetData;
    private @Nullable String decodedString;
    private ResourcePackStatus resourcePackStatus;

    private final boolean legacyPacket = IProtocolAccess.get().getServer().getVersion().lessThan(Version.v1_11);

    public PacketPlayInResourcePackStatus(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(legacyPacket) {
            this.decodedString = packetData.readString(0);
        } else {
            this.decodedString = "Not supported by this version";
        }
        this.resourcePackStatus = ResourcePackStatus.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getResourcePackStatusEnum()).ordinal());
    }

    public PacketPlayInResourcePackStatus(@Nullable String decodedString, ResourcePackStatus resourcePackStatus) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(decodedString, 0),
                        new IndexedParam<>(resourcePackStatus.original(), 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, resourcePackStatus.original()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.decodedString = decodedString;
        this.resourcePackStatus = resourcePackStatus;
    }

    @Nullable
    public String getDecodedString() {
        return decodedString;
    }

    public void setDecodedString(@Nullable String decodedString) {
        if(legacyPacket) this.packetData.writeString(0, decodedString);
        this.decodedString = decodedString;
    }

    public ResourcePackStatus getResourcePackStatus() {
        return resourcePackStatus;
    }

    public void setResourcePackStatus(@NotNull ResourcePackStatus resourcePackStatus) {
        this.packetData.writeEnumConstant(0, resourcePackStatus.original());
        this.resourcePackStatus = resourcePackStatus;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.RESOURCEPACK_STATUS;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    public enum ResourcePackStatus {
        SUCCESSFULLY_LOADED(0),
        DECLINED(1),
        FAILED_DOWNLOAD(2),
        ACCEPTED(3);

        private final int id;

        ResourcePackStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Contract(pure = true)
        public static @Nullable ResourcePackStatus getById(int id) {
            for(ResourcePackStatus status : values()) {
                if(status.id == id) return status;
            }
            return null;
        }

        public @NotNull Enum<?> original() {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getResourcePackStatusEnum(), this.id);
        }
    }
}
