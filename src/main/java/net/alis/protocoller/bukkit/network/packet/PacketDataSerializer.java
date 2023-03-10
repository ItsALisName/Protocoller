package net.alis.protocoller.bukkit.network.packet;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.AlMinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.entity.player.PlayerAbilities;
import net.alis.protocoller.samples.network.MinecraftPacketDataSerializer;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.phys.BaseBlockPosition;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.ObjectAccessor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.PublicKey;
import java.util.*;

public class PacketDataSerializer extends ObjectAccessor implements PacketDataContainer, Cloneable {

    private final MinecraftPacketType packetType;
    private @Nullable Player player;

    public PacketDataSerializer(Object rawPacket, @Nullable Player player) {
        super(rawPacket);
        this.player = player;
        this.packetType = PacketTypesInitializer.setPacketType(rawPacket);
    }

    public PacketDataSerializer(Object rawPacket) {
        this(rawPacket, null);
    }

    public PacketDataSerializer(@NotNull PacketDataSerializer serializer) {
        super(new ObjectAccessor(serializer.getObject()).getObject());
        packetType = serializer.packetType;
    }

    public MinecraftPacketType getType() {
        return packetType;
    }

    @Nullable
    @Override
    public Player getPlayer() {
        return player;
    }

    public Object getRawPacket() {
        return super.getObject();
    }

    public boolean readBoolean(int index) {
        return super.read(index, boolean.class);
    }

    public byte readByte(int index) {
        return super.read(index, byte.class);
    }

    public short readShort(int index) {
        return super.read(index, short.class);
    }

    public int readInt(int index) {
        return super.read(index, int.class);
    }

    public Optional<?> readOptional(int index) {
        return super.read(index, Optional.class);
    }

    public long readLong(int index) {
        return super.read(index, long.class);
    }

    public Enum<?> readEnum(int index) {
        return super.read(index, Enum.class);
    }

    public MinecraftPacketDataSerializer readOriginalDataSerializer(int index) {
        return new MinecraftPacketDataSerializer((Object) super.read(index, ClassesContainer.INSTANCE.getPacketDataSerializerClass()));
    }

    @Override
    public UUID readUUID(int index) {
        return super.read(index, UUID.class);
    }

    @Override
    public Collection<Object> readCollection(int index) {
        return super.read(index, Collection.class);
    }

    @Override
    public Map<Object, Object> readMap(int index) {
        return super.read(index, Map.class);
    }

    @Override
    public Set<Object> readSet(int index) {
        return super.read(index, Set.class);
    }

    public Entity readMinecraftEntity(int index) {
        return AlMinecraftReflection.entityFromMinecraftEntity(super.read(index, ClassesContainer.INSTANCE.getMinecraftEntityClass()));
    }

    public PlayerAbilities readPlayerAbilities(int index) {
        return new PlayerAbilities(super.read(index, ClassesContainer.INSTANCE.getPlayerAbilitiesClass()));
    }

    public BlockPosition readBlockPosition(int index) {
        return new BlockPosition(new BaseBlockPosition(super.read(index, ClassesContainer.INSTANCE.getBlockPositionClass()), false));
    }

    public BaseBlockPosition readBaseBlockPosition(int index) {
        return new BaseBlockPosition(super.read(index, ClassesContainer.INSTANCE.getBaseBlockPositionClass()), true);
    }

    public MinecraftKey readMinecraftKey(int index) {
        return new MinecraftKey((Object) super.read(0, ClassesContainer.INSTANCE.getMinecraftKeyClass()));
    }

    public ChatComponent readIChatBaseComponent(int index) {
        return new ChatComponent(ChatSerializer.fromComponent(super.read(index, ClassesContainer.INSTANCE.getIChatBaseComponentClass())));
    }

    public float readFloat(int index) {
        return super.read(index, float.class);
    }

    public double readDouble(int index) {
        return super.read(index, double.class);
    }

    public boolean[] readBooleanArray(int index) {
        return super.read(index, boolean[].class);
    }

    public <T> T readList(int index) {
        return super.read(index, List.class);
    }

    public PublicKey readPublicKey(int index) {
        return super.read(index, PublicKey.class);
    }

    public byte[] readByteArray(int index) {
        return super.read(index, byte[].class);
    }

    public short[] readShortArray(int index) {
        return super.read(index, short[].class);
    }

    public int[] readIntArray(int index) {
        return super.read(index, int[].class);
    }

    public long[] readLongArray(int index) {
        return super.read(index, long[].class);
    }

    public float[] readFloatArray(int index) {
        return super.read(index, float[].class);
    }

    public double[] readDoubleArray(int index) {
        return super.read(index, double[].class);
    }

    public String[] readStringArray(int index) {
        return super.read(index, String[].class);
    }

    public GameProfile readGameProfile(int index) {
        return new GameProfile(super.read(index, ClassesContainer.INSTANCE.getGameProfileClass()));
    }

    public ServerPing readServerPing(int index) {
        return new ServerPing(super.read(0, ClassesContainer.INSTANCE.getServerPingClass()));
    }

    public String readString(int index) {
        return super.read(index, String.class);
    }

    public <T> T readObject(int index, Class<?> type) {
        return super.read(index, type);
    }

    public Enum<?> readEnumConstant(int index, Class<? extends Enum<?>> type) {
        return super.read(index, type);
    }

    public ItemStack readMinecraftItemStack(int index) {
        return AlMinecraftReflection.itemStackFromMinecraftItemStack(super.read(index, ClassesContainer.INSTANCE.getMinecraftItemStackClass()));
    }


    @Override
    public void writeUUID(int index, UUID uuid) {
        super.write(index, uuid);
    }

    public void writeBaseBlockPosition(int index, @NotNull BaseBlockPosition position) {
        super.writeSpecify(index, ClassesContainer.INSTANCE.getBaseBlockPositionClass(), position.createOriginal());
    }

    @Override
    public void writeCollection(int index, Collection<Object> collection) {
        super.write(index, collection);
    }

    @Override
    public void writeMap(int index, Map<Object, Object> map) {
        super.write(index, map);
    }

    @Override
    public void writeSet(int index, Set<Object> set) {
        super.write(index, set);
    }

    public void writeMinecraftItemStack(int index, ItemStack stack) {
        super.writeSpecify(index,ClassesContainer.INSTANCE.getMinecraftItemStackClass(), AlMinecraftReflection.getMinecraftItemStack(stack));
    }

    public void writePlayerAbilities(int index, @NotNull PlayerAbilities abilities) {
        super.write(index, abilities.createOriginal());
    }

    public void writeBlockPosition(int index, @NotNull BlockPosition position) {
        super.writeSpecify(index, ClassesContainer.INSTANCE.getBlockPositionClass(), position.createOriginal());
    }

    public void writeMinecraftKey(int index, @NotNull MinecraftKey key) {
        super.write(index, key.createOriginal());
    }

    public void writeBoolean(int index, boolean param) {
        super.write(index, param);
    }

    public void writeByte(int index, byte param) {
        super.write(index, param);
    }

    public void writeShort(int index, short param) {
        super.write(index, param);
    }

    public void writeIChatBaseComponent(int index, @NotNull ChatComponent component) {
        super.writeSpecify(index, ClassesContainer.INSTANCE.getIChatBaseComponentClass(), component.asIChatBaseComponent());
    }

    public void writeServerPing(int index, @NotNull ServerPing param) {
        super.write(index, param.createOriginal());
    }

    public void writeInt(int index, int param) {
        super.write(index, param);
    }

    public void writeGameProfile(int index, @NotNull GameProfile profile) {
        super.write(index, profile.createOriginal());
    }

    public void writeOptional(int index, Optional<?> param) {
        super.write(index, param);
    }

    public void writeOriginalDataSerializer(int index, @NotNull MinecraftPacketDataSerializer serializer) {
        super.write(index, serializer.createOriginal());
    }

    public void writePublicKey(int index, PublicKey key) {
        super.write(index, key);
    }

    public void writeList(int index, List<?> param) {
        super.write(index, param);
    }

    public void writeLong(int index, long param) {
        super.write(index, param);
    }

    public void writeFloat(int index, float param) {
        super.write(index, param);
    }

    public void writeDouble(int index, double param) {
        super.writeSpecify(index, double.class, param);
    }

    public void writeString(int index, String param) {
        super.write(index, param);
    }

    public void writeObject(int index, Object param) {
        super.write(index, param);
    }

    public void writeBooleanArray(int index, boolean[] array) {
        super.write(index, array);
    }

    public void writeByteArray(int index, byte[] param) {
        super.write(index, param);
    }

    public void writeShortArray(int index, short[] param) {
        super.write(index, param);
    }

    public void writeIntArray(int index, int[] param) {
        super.write(index, param);
    }

    public void writeLongArray(int index, long[] param) {
        super.write(index, param);
    }

    public void writeFloatArray(int index, float[] param) {
        super.write(index, param);
    }

    public void writeDoubleArray(int index, double[] param) {
        super.write(index, param);
    }

    public void writeStringArray(int index, String[] param) {
        super.write(index, param);
    }

    public void writeEnumConstant(int index, Enum<?> enumConstant) {
        super.write(index, enumConstant);
    }

    public void writeMinecraftEntity(int index, Entity entity) {
        super.writeSpecify(index, ClassesContainer.INSTANCE.getMinecraftEntityClass(), AlMinecraftReflection.getMinecraftEntity(entity));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

