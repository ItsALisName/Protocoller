package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.phys.MovingObjectPositionBlock;
import net.alis.protocoller.parent.phys.Vector3D;
import net.alis.protocoller.parent.util.Facing;
import net.alis.protocoller.util.annotations.AddedSince;
import net.alis.protocoller.util.annotations.NotOnAllVersions;

import static net.alis.protocoller.bukkit.enums.Version.v1_9;

@AddedSince(v1_9)
public class PacketPlayInUseItem implements Packet {

    private final PacketDataSerializer packetData;
    private MovingObjectPositionBlock blockHitResult;
    private PacketPlayInArmAnimation.Hand hand;

    private @NotOnAllVersions float decodedFloat$0;
    private @NotOnAllVersions float decodedFloat$1;
    private @NotOnAllVersions float decodedFloat$2;

    private @NotOnAllVersions int sequence;

    public PacketPlayInUseItem(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.hand = PacketPlayInArmAnimation.Hand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getHandEnum()).ordinal());
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_14)) {
            BlockPosition pos = packetData.readBlockPosition(0);
            this.blockHitResult = new MovingObjectPositionBlock(
                    false,
                    new Vector3D(pos.getX(), pos.getY(), pos.getZ()),
                    Facing.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getDirectionEnum()).ordinal()),
                    pos,
                    false
            );
            this.decodedFloat$0 = packetData.readFloat(0);
            this.decodedFloat$1 = packetData.readFloat(1);
            this.decodedFloat$2 = packetData.readFloat(2);
            this.sequence = 0;
        } else {
            this.decodedFloat$0 = 0.0F; this.decodedFloat$1 = 0.0F; this.decodedFloat$2 = 0.0F;
            this.blockHitResult = new MovingObjectPositionBlock(packetData.readObject(0, ClassesContainer.INSTANCE.getMovingObjectPositionBlockClass()));
            if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19)) {
                this.sequence = packetData.readInt(0);
            } else {
                this.sequence = 0;
            }
        }
    }

    /**
     * For versions less than 1.14
     */
    public PacketPlayInUseItem(MovingObjectPositionBlock blockHitResult, PacketPlayInArmAnimation.Hand hand, int sequence, float f1, float f2, float f3) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_14)) {
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
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, hand.original(), blockHitResult.createOriginal()));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(creator.create(null, hand.original(), blockHitResult.createOriginal(), sequence));
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
    public PacketPlayInUseItem(MovingObjectPositionBlock blockHitResult, PacketPlayInArmAnimation.Hand hand) {
        this(blockHitResult, hand, 0, 0, 0, 0);
    }

    /**
     * For version 1.19+
     */
    public PacketPlayInUseItem(MovingObjectPositionBlock blockHitResult, PacketPlayInArmAnimation.Hand hand, int sequence) {
        this(blockHitResult, hand, sequence, 0, 0, 0);
    }

    public MovingObjectPositionBlock getBlockHitResult() {
        return blockHitResult;
    }

    public void setBlockHitResult(MovingObjectPositionBlock blockHitResult) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_14)) this.packetData.writeBlockPosition(0, blockHitResult.getPosition());
        this.blockHitResult = blockHitResult;
    }

    public PacketPlayInArmAnimation.Hand getHand() {
        return hand;
    }

    public void setHand(PacketPlayInArmAnimation.Hand hand) {
        this.packetData.writeEnumConstant(0, hand.original());
        this.hand = hand;
    }

    public float getDecodedFloat$0() {
        return decodedFloat$0;
    }

    public void setDecodedFloat$0(float decodedFloat$0) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_14)) this.packetData.writeFloat(0, decodedFloat$0);
        this.decodedFloat$0 = decodedFloat$0;
    }

    public float getDecodedFloat$1() {
        return decodedFloat$1;
    }

    public void setDecodedFloat$1(float decodedFloat$1) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_14)) this.packetData.writeFloat(1, decodedFloat$1);
        this.decodedFloat$1 = decodedFloat$1;
    }

    public float getDecodedFloat$2() {
        return decodedFloat$2;
    }

    public void setDecodedFloat$2(float decodedFloat$2) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_14)) this.packetData.writeFloat(2, decodedFloat$2);
        this.decodedFloat$2 = decodedFloat$2;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19)) this.packetData.writeInt(0, sequence);
        this.sequence = sequence;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.USE_ITEM;
    }

    @Override
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
