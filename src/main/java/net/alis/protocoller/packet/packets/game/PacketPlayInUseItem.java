package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.entity.Hand;
import net.alis.protocoller.samples.phys.MovingObjectPositionBlock;
import net.alis.protocoller.samples.phys.Vector3D;
import net.alis.protocoller.samples.util.Direction;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInUseItem implements PlayInPacket {

    private final PacketDataContainer packetData;
    private MovingObjectPositionBlock blockHitResult;
    private Hand hand;

    private @Nullable float decodedFloat$0;
    private @Nullable float decodedFloat$1;
    private @Nullable float decodedFloat$2;

    private @Nullable int sequence;

    private final boolean legacyPacket = IProtocolAccess.get().getServer().getVersion().lessThan(Version.v1_14);
    private final boolean modernPacket = IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19);
    
    public PacketPlayInUseItem(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.hand = Hand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getHandEnum()).ordinal());
        if(legacyPacket) {
            BlockPosition pos = packetData.readBlockPosition(0);
            this.blockHitResult = new MovingObjectPositionBlock(
                    false,
                    new Vector3D(pos.getX(), pos.getY(), pos.getZ()),
                    Direction.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getDirectionEnum()).ordinal()),
                    pos,
                    false
            );
            this.decodedFloat$0 = packetData.readFloat(0);
            this.decodedFloat$1 = packetData.readFloat(1);
            this.decodedFloat$2 = packetData.readFloat(2);
            this.sequence = 0;
        } else {
            this.decodedFloat$0 = 0.0F; this.decodedFloat$1 = 0.0F; this.decodedFloat$2 = 0.0F;
            this.blockHitResult = new MovingObjectPositionBlock(packetData.readObject(0, ClassAccessor.get().getMovingObjectPositionBlockClass()));
            if(modernPacket) {
                this.sequence = packetData.readInt(0);
            } else {
                this.sequence = 0;
            }
        }
    }

    /**
     * For versions less than 1.14
     */
    public PacketPlayInUseItem(MovingObjectPositionBlock blockHitResult, Hand hand, int sequence, float f1, float f2, float f3) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(IProtocolAccess.get().getServer().getVersion().lessThan(Version.v1_14)) {
                    params = new IndexedParam[] {
                        new IndexedParam<>(blockHitResult.getPosition().createOriginal(), 0),
                        new IndexedParam<>(blockHitResult.getFacing().original(), 0),
                        new IndexedParam<>(hand.original(), 0),
                        new IndexedParam<>(f1, 0),
                        new IndexedParam<>(f2, 1),
                        new IndexedParam<>(f3, 2)
                    };
                } else {
                    params = new IndexedParam[] {
                        new IndexedParam<>(blockHitResult.createOriginal(), 0),
                        new IndexedParam<>(hand.original(), 0)
                    };
                }
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, hand.original(), blockHitResult.createOriginal()));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, hand.original(), blockHitResult.createOriginal(), sequence));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.blockHitResult = blockHitResult;
        this.hand = hand;
        this.sequence = sequence;
    }

    /**
     * For versions 1.17 - 1.19
     */
    public PacketPlayInUseItem(MovingObjectPositionBlock blockHitResult, Hand hand) {
        this(blockHitResult, hand, 0, 0, 0, 0);
    }

    /**
     * For version 1.19+
     */
    public PacketPlayInUseItem(MovingObjectPositionBlock blockHitResult, Hand hand, int sequence) {
        this(blockHitResult, hand, sequence, 0, 0, 0);
    }

    public MovingObjectPositionBlock getBlockHitResult() {
        return blockHitResult;
    }

    public void setBlockHitResult(MovingObjectPositionBlock blockHitResult) {
        if(legacyPacket) this.packetData.writeBlockPosition(0, blockHitResult.getPosition());
        this.blockHitResult = blockHitResult;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(@NotNull Hand hand) {
        this.packetData.writeEnumConstant(0, hand.original());
        this.hand = hand;
    }

    public float getDecodedFloat$0() {
        return decodedFloat$0;
    }

    public void setDecodedFloat$0(float decodedFloat$0) {
        if(legacyPacket) this.packetData.writeFloat(0, decodedFloat$0);
        this.decodedFloat$0 = decodedFloat$0;
    }

    public float getDecodedFloat$1() {
        return decodedFloat$1;
    }

    public void setDecodedFloat$1(float decodedFloat$1) {
        if(legacyPacket) this.packetData.writeFloat(1, decodedFloat$1);
        this.decodedFloat$1 = decodedFloat$1;
    }

    public float getDecodedFloat$2() {
        return decodedFloat$2;
    }

    public void setDecodedFloat$2(float decodedFloat$2) {
        if(legacyPacket) this.packetData.writeFloat(2, decodedFloat$2);
        this.decodedFloat$2 = decodedFloat$2;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        if(modernPacket) this.packetData.writeInt(0, sequence);
        this.sequence = sequence;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.USE_ITEM;
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
