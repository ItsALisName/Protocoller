package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.network.packet.IndexedParam;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.entity.block.BlockMirror;
import net.alis.protocoller.samples.entity.block.BlockRotation;
import net.alis.protocoller.samples.entity.block.TileEntityStructureUpdateType;
import net.alis.protocoller.samples.entity.block.properties.BlockPropertyStructureMode;
import net.alis.protocoller.samples.phys.BaseBlockPosition;
import net.alis.protocoller.util.annotations.AddedSince;
import org.jetbrains.annotations.NotNull;

import static net.alis.protocoller.plugin.enums.Version.v1_13;

@AddedSince(v1_13)
public class PacketPlayInStruct implements PlayInPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private TileEntityStructureUpdateType action;
    private BlockPropertyStructureMode mode;
    private String templateName;
    private BlockPosition offset;
    private BaseBlockPosition size;
    private BlockMirror mirror;
    private BlockRotation rotation;
    private String metadata;
    private boolean ignoreEntities;
    private boolean showAir;
    private boolean showBoundingBox;
    private float integrity;
    private long seed;

    public PacketPlayInStruct(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.action = TileEntityStructureUpdateType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getTileEntityStructureUpdateType()).ordinal());
        this.mode = BlockPropertyStructureMode.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getBlockPropertyStructureModeEnum()).ordinal());
        this.templateName = packetData.readString(0);
        this.offset = packetData.readBlockPosition(1);
        this.size = packetData.readBaseBlockPosition(0);
        this.mirror = BlockMirror.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getBlockMirrorEnum()).ordinal());
        this.rotation = BlockRotation.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getBlockRotationEnum()).ordinal());
        this.metadata = packetData.readString(1);
        this.ignoreEntities = packetData.readBoolean(0);
        this.showAir = packetData.readBoolean(1);
        this.showBoundingBox = packetData.readBoolean(2);
        this.integrity = packetData.readFloat(0);
        this.seed = packetData.readLong(0);
    }

    public PacketPlayInStruct(BlockPosition position, TileEntityStructureUpdateType action, BlockPropertyStructureMode mode, String templateName, BlockPosition offset, BaseBlockPosition size, BlockMirror mirror, BlockRotation rotation, String metadata, boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity, long seed) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                    new IndexedParam<>(position.createOriginal(), 0),
                    new IndexedParam<>(action.original(), 0),
                    new IndexedParam<>(mode.original(), 0),
                    new IndexedParam<>(templateName, 0),
                    new IndexedParam<>(offset.createOriginal(), 1),
                    new IndexedParam<>(size.createOriginal(), 0),
                    new IndexedParam<>(mirror.original(), 0),
                    new IndexedParam<>(rotation.original(), 0),
                    new IndexedParam<>(metadata, 1),
                    new IndexedParam<>(ignoreEntities, 0),
                    new IndexedParam<>(showAir, 1),
                    new IndexedParam<>(showBoundingBox, 2),
                    new IndexedParam<>(integrity, 0),
                    new IndexedParam<>(seed, 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, position.createOriginal(), action.original(), mode.original(), templateName, offset.createOriginal(), size.createOriginal(), mirror.original(), rotation.original(), metadata, ignoreEntities, showAir, showBoundingBox, integrity, seed));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.position = position;
        this.action = action;
        this.mode = mode;
        this.templateName = templateName;
        this.offset = offset;
        this.size = size;
        this.mirror = mirror;
        this.rotation = rotation;
        this.metadata = metadata;
        this.ignoreEntities = ignoreEntities;
        this.showAir = showAir;
        this.showBoundingBox = showBoundingBox;
        this.integrity = integrity;
        this.seed = seed;
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public TileEntityStructureUpdateType getAction() {
        return action;
    }

    public void setAction(@NotNull TileEntityStructureUpdateType action) {
        this.packetData.writeEnumConstant(0, action.original());
        this.action = action;
    }

    public BlockPropertyStructureMode getMode() {
        return mode;
    }

    public void setMode(@NotNull BlockPropertyStructureMode mode) {
        this.packetData.writeEnumConstant(0, mode.original());
        this.mode = mode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.packetData.writeString(0, templateName);
        this.templateName = templateName;
    }

    public BlockPosition getOffset() {
        return offset;
    }

    public void setOffset(BlockPosition offset) {
        this.packetData.writeBlockPosition(1, offset);
        this.offset = offset;
    }

    public BaseBlockPosition getSize() {
        return size;
    }

    public void setSize(BaseBlockPosition size) {
        this.packetData.writeBaseBlockPosition(0, size);
        this.size = size;
    }

    public BlockMirror getMirror() {
        return mirror;
    }

    public void setMirror(@NotNull BlockMirror mirror) {
        this.packetData.writeEnumConstant(0, mirror.original());
        this.mirror = mirror;
    }

    public BlockRotation getRotation() {
        return rotation;
    }

    public void setRotation(@NotNull BlockRotation rotation) {
        this.packetData.writeEnumConstant(0, rotation.original());
        this.rotation = rotation;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.packetData.writeString(1, metadata);
        this.metadata = metadata;
    }

    public boolean isIgnoreEntities() {
        return ignoreEntities;
    }

    public void setIgnoreEntities(boolean ignoreEntities) {
        this.packetData.writeBoolean(0, ignoreEntities);
        this.ignoreEntities = ignoreEntities;
    }

    public boolean isShowAir() {
        return showAir;
    }

    public void setShowAir(boolean showAir) {
        this.packetData.writeBoolean(1, showAir);
        this.showAir = showAir;
    }

    public boolean isShowBoundingBox() {
        return showBoundingBox;
    }

    public void setShowBoundingBox(boolean showBoundingBox) {
        this.packetData.writeBoolean(2, showBoundingBox);
        this.showBoundingBox = showBoundingBox;
    }

    public float getIntegrity() {
        return integrity;
    }

    public void setIntegrity(float integrity) {
        this.packetData.writeFloat(0, integrity);
        this.integrity = integrity;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.packetData.writeLong(0, seed);
        this.seed = seed;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.STRUCT;
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
