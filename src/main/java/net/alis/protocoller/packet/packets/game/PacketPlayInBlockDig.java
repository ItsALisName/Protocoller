package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.util.Facing;
import net.alis.protocoller.util.annotations.NotOnAllVersions;

public class PacketPlayInBlockDig implements Packet {

    private final PacketDataContainer packetData;
    private PlayerDigType digType;
    private BlockPosition blockPosition;
    private Facing facing;
    private @NotOnAllVersions int sequence;

    public PacketPlayInBlockDig(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.digType = PlayerDigType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getPlayerDigTypeEnum()).ordinal());
        this.blockPosition = packetData.readBlockPosition(0);
        this.facing = Facing.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getDirectionEnum()).ordinal());
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19)) {
            this.sequence = packetData.readInt(0);
        } else {
            this.sequence = 0;
        }
    }

    public PacketPlayInBlockDig(PlayerDigType digType, BlockPosition blockPosition, Facing facing, int sequence) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(blockPosition.createOriginal(), 0),
                        new IndexedParam<>(facing.original(), 0),
                        new IndexedParam<>(digType.original(), 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, digType.original(), blockPosition.createOriginal(), facing.original()));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, digType.original(), blockPosition.createOriginal(), facing.original(), sequence));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.digType = digType;
        this.blockPosition = blockPosition;
        this.facing = facing;
        this.sequence = sequence;
    }



    public PlayerDigType getDigType() {
        return digType;
    }

    public void setDigType(PlayerDigType digType) {
        this.packetData.writeEnumConstant(0, digType.original());
        this.digType = digType;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19)) {
            this.packetData.writeInt(0, sequence);
        }
        this.sequence = sequence;
    }

    public Facing getFacing() {
        return facing;
    }

    public void setFacing(Facing facing) {
        this.packetData.writeEnumConstant(0, facing.original());
        this.facing = facing;
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public void setBlockPosition(BlockPosition blockPosition) {
        this.packetData.writeBlockPosition(0, blockPosition);
        this.blockPosition = blockPosition;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.BLOCK_DIG;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }

    public enum PlayerDigType {
        START_DESTROY_BLOCK(0),
        ABORT_DESTROY_BLOCK(1),
        STOP_DESTROY_BLOCK(2),
        DROP_ALL_ITEMS(3),
        DROP_ITEM(4),
        RELEASE_USE_ITEM(5),
        SWAP_HELD_ITEMS(6);

        private final int id;

        PlayerDigType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static PlayerDigType getById(int id) {
            for(PlayerDigType s : PlayerDigType.values())
                if(s.id == id) return s;
            return null;
        }

        public Enum<?> original() {
            return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getPlayerDigTypeEnum(), this.id);
        }
    }

}
