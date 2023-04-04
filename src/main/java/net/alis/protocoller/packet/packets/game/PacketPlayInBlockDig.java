package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.network.packet.IndexedParam;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.util.Direction;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInBlockDig implements PlayInPacket {

    private final PacketDataContainer packetData;
    private PlayerDigType digType;
    private BlockPosition blockPosition;
    private Direction direction;
    private int sequence;
    
    private final boolean modernPacket = GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19);

    public PacketPlayInBlockDig(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.digType = PlayerDigType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getPlayerDigTypeEnum()).ordinal());
        this.blockPosition = packetData.readBlockPosition(0);
        this.direction = Direction.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getDirectionEnum()).ordinal());
        if(modernPacket) {
            this.sequence = packetData.readInt(0);
        } else {
            this.sequence = 0;
        }
    }

    public PacketPlayInBlockDig(PlayerDigType digType, BlockPosition blockPosition, Direction direction, int sequence) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(blockPosition.createOriginal(), 0),
                        new IndexedParam<>(direction.original(), 0),
                        new IndexedParam<>(digType.original(), 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, digType.original(), blockPosition.createOriginal(), direction.original()));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, digType.original(), blockPosition.createOriginal(), direction.original(), sequence));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.digType = digType;
        this.blockPosition = blockPosition;
        this.direction = direction;
        this.sequence = sequence;
    }



    public PlayerDigType getDigType() {
        return digType;
    }

    public void setDigType(@NotNull PlayerDigType digType) {
        this.packetData.writeEnumConstant(0, digType.original());
        this.digType = digType;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        if(modernPacket) this.packetData.writeInt(0, sequence);
        this.sequence = sequence;
    }

    public Direction getFacing() {
        return direction;
    }

    public void setFacing(@NotNull Direction direction) {
        this.packetData.writeEnumConstant(0, direction.original());
        this.direction = direction;
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
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

        @Contract(pure = true)
        public static @Nullable PlayerDigType getById(int id) {
            for(PlayerDigType s : PlayerDigType.values())
                if(s.id == id) return s;
            return null;
        }

        public @NotNull Enum<?> original() {
            return BaseReflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getPlayerDigTypeEnum(), this.id);
        }
    }

}
