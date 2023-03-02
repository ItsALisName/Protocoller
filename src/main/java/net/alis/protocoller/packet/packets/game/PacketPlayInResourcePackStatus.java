package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.util.annotations.NotOnAllVersions;

public class PacketPlayInResourcePackStatus implements Packet {

    private final PacketDataSerializer packetData;
    private @NotOnAllVersions String decodedString;
    private ResourcePackStatus resourcePackStatus;

    private final boolean legacyPack = GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_11);

    public PacketPlayInResourcePackStatus(PacketDataSerializer packetData) {
        this.packetData = packetData;
        if(legacyPack) {
            this.decodedString = packetData.readString(0);
        } else {
            this.decodedString = "Not supported by this version";
        }
        this.resourcePackStatus = ResourcePackStatus.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getResourcePackStatusEnum()).ordinal());
    }

    public PacketPlayInResourcePackStatus(@NotOnAllVersions String decodedString, ResourcePackStatus resourcePackStatus) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(decodedString, 0),
                        new IndexedParam<>(resourcePackStatus.original(), 0)
                };
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, resourcePackStatus.original()));
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

    @NotOnAllVersions
    public String getDecodedString() {
        return decodedString;
    }

    public void setDecodedString(@NotOnAllVersions String decodedString) {
        if(legacyPack) this.packetData.writeString(0, decodedString);
        this.decodedString = decodedString;
    }

    public ResourcePackStatus getResourcePackStatus() {
        return resourcePackStatus;
    }

    public void setResourcePackStatus(ResourcePackStatus resourcePackStatus) {
        this.packetData.writeEnumConstant(0, resourcePackStatus.original());
        this.resourcePackStatus = resourcePackStatus;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.RESOURCEPACK_STATUS;
    }

    @Override
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
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

        public static ResourcePackStatus getById(int id) {
            for(ResourcePackStatus status : values()) {
                if(status.id == id) return status;
            }
            return null;
        }

        public Enum<?> original() {
            return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getResourcePackStatusEnum(), this.id);
        }
    }
}
