package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTTagCompound;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ClientboundLevelChunkPacketData implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private NBTTagCompound heightmaps;
    private byte[] buffer;
    private List<BlockEntityInfo> blockEntityData;

    public ClientboundLevelChunkPacketData(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.heightmaps = new NBTTagCompound();
        heightmaps.merge(packetData.readObject(0, ClassAccessor.get().getNbtTagCompoundClass()));
        this.buffer = packetData.readByteArray(0);
        this.blockEntityData = new ArrayList<>();
        for(Object blockentdata : (List<Object>)packetData.readList(0)) this.blockEntityData.add(new BlockEntityInfo(blockentdata));
    }

    public ClientboundLevelChunkPacketData(Chunk chunk) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, MinecraftReflection.getMinecraftChunk(chunk)));
        this.heightmaps = new NBTTagCompound();
        heightmaps.merge(packetData.readObject(0, ClassAccessor.get().getNbtTagCompoundClass()));
        this.buffer = packetData.readByteArray(0);
        this.blockEntityData = new ArrayList<>();
        for(Object blockentdata : (List<Object>)packetData.readList(0)) this.blockEntityData.add(new BlockEntityInfo(blockentdata));
    }

    public ClientboundLevelChunkPacketData(@NotNull Object rawPacketData) {
        this.packetData = new PacketDataSerializer(rawPacketData);
        this.heightmaps = new NBTTagCompound();
        heightmaps.merge(packetData.readObject(0, ClassAccessor.get().getNbtTagCompoundClass()));
        this.buffer = packetData.readByteArray(0);
        this.blockEntityData = new ArrayList<>();
        for(Object blockentdata : (List<Object>)packetData.readList(0)) this.blockEntityData.add(new BlockEntityInfo(blockentdata));
    }

    public NBTTagCompound getHeightmaps() {
        return heightmaps;
    }

    public void setHeightmaps(NBTTagCompound heightmaps) {
        this.packetData.writeObject(0, heightmaps.toOriginal());
        this.heightmaps = heightmaps;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.packetData.writeByteArray(0, buffer);
        this.buffer = buffer;
    }

    public List<BlockEntityInfo> getBlockEntityData() {
        return blockEntityData;
    }

    public void setBlockEntityData(List<BlockEntityInfo> blockEntityData) {
        List<Object> blockentdata = new ArrayList<>();
        for(BlockEntityInfo info : blockEntityData) blockentdata.add(info.createOriginal());
        this.packetData.writeList(0, blockentdata);
        this.blockEntityData = blockEntityData;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.LEVEL_CHUNK_PACKET_DATA;
    }

    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    public static class BlockEntityInfo implements ObjectSample {
        private int packedXZ;
        private int y;
        private final Object type;
        @Nullable
        private NBTTagCompound tag;
        
        public BlockEntityInfo(Object blockEntityInfo) {
            AccessedObject object = new AccessedObject(blockEntityInfo);
            this.packedXZ = object.read(0, Integer.TYPE);
            this.y = object.read(1, Integer.TYPE);
            this.type = object.read(0, ClassAccessor.get().getTileEntityTypesClass());
            try {
                Object rawNbt = object.read(0, ClassAccessor.get().getNbtTagCompoundClass());
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.merge(rawNbt);
                this.tag = nbt;
            } catch (Exception e) {
                this.tag = null;
            }
        }

        public int getPackedXZ() {
            return packedXZ;
        }

        public void setPackedXZ(int packedXZ) {
            this.packedXZ = packedXZ;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Object getType() {
            return type;
        }

        @Nullable
        public NBTTagCompound getTag() {
            return tag;
        }

        public void setTag(@NotNull NBTTagCompound tag) {
            this.tag = tag;
        }

        @Override
        public Object createOriginal() {
            return Reflect.callConstructor(
                    Reflect.getConstructor(ClassAccessor.get().getBlockEntityInfoClass(), Integer.TYPE, Integer.TYPE, ClassAccessor.get().getTileEntityTypesClass(), ClassAccessor.get().getNbtTagCompoundClass()),
                    this.packedXZ, this.y, this.type, this.tag.toOriginal()
            );
        }
    }
    
}
