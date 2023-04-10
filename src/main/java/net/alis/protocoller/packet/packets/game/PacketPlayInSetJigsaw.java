package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.entity.block.TileEntityJigsawJointType;
import net.alis.protocoller.samples.resources.MinecraftKey;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInSetJigsaw implements PlayInPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private MinecraftKey name;
    private MinecraftKey target;
    private @Nullable MinecraftKey pool;
    private String finalState;
    private @Nullable TileEntityJigsawJointType jointType;
    
    private final boolean modernPacket = GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16);

    public PacketPlayInSetJigsaw(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.name = packetData.readMinecraftKey(0);
        this.target = packetData.readMinecraftKey(1);
        this.finalState = packetData.readString(0);
        if(modernPacket) {
            this.pool = packetData.readMinecraftKey(2);
            this.jointType = TileEntityJigsawJointType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getTileEntityJigsawJointypeEnum()).ordinal());
        } else {
            this.pool = new MinecraftKey("unsupported:old_version");
            this.jointType = TileEntityJigsawJointType.ROLLABLE;
        }
    }

    public PacketPlayInSetJigsaw(BlockPosition position, MinecraftKey name, MinecraftKey target, @Nullable MinecraftKey pool, String finalState, @Nullable TileEntityJigsawJointType jointType) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) {
                    params = new IndexedParam[]{
                            new IndexedParam<>(position.createOriginal(), 0),
                            new IndexedParam<>(name.createOriginal(), 0),
                            new IndexedParam<>(target.createOriginal(), 1),
                            new IndexedParam<>(pool.createOriginal(), 2),
                            new IndexedParam<>(finalState, 0),
                            new IndexedParam<>(jointType.original(), 0)
                    };
                } else {
                    params = new IndexedParam[]{
                            new IndexedParam<>(position.createOriginal(), 0),
                            new IndexedParam<>(name.createOriginal(), 0),
                            new IndexedParam<>(target.createOriginal(), 1),
                            new IndexedParam<>(finalState, 0)
                    };
                }
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, position.createOriginal(), name.createOriginal(), target.createOriginal(), pool.createOriginal(), finalState, jointType.original()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.position = position;
        this.name = name;
        this.target = target;
        this.pool = pool;
        this.finalState = finalState;
        this.jointType = jointType;
    }

    public PacketPlayInSetJigsaw(BlockPosition position, MinecraftKey name, MinecraftKey target, String finalState) {
        this(position, name, target, null, finalState, null);
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public MinecraftKey getName() {
        return name;
    }

    public void setName(MinecraftKey name) {
        this.packetData.writeMinecraftKey(0, name);
        this.name = name;
    }

    public MinecraftKey getTarget() {
        return target;
    }

    public void setTarget(MinecraftKey target) {
        this.packetData.writeMinecraftKey(1, target);
        this.target = target;
    }

    @Nullable
    public MinecraftKey getPool() {
        return pool;
    }

    public void setPool(@Nullable MinecraftKey pool) {
        if(modernPacket) this.packetData.writeMinecraftKey(2, pool);
        this.pool = pool;
    }

    public String getFinalState() {
        return finalState;
    }

    public void setFinalState(String finalState) {
        this.packetData.writeString(0, finalState);
        this.finalState = finalState;
    }

    @Nullable
    public TileEntityJigsawJointType getJointType() {
        return jointType;
    }

    public void setJointType(@Nullable TileEntityJigsawJointType jointType) {
        if(modernPacket) this.packetData.writeEnumConstant(0, jointType.original());
        this.jointType = jointType;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.SET_JIGSAW;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
