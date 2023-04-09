package net.alis.protocoller.packet;

import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.entity.player.PlayerAbilities;
import net.alis.protocoller.samples.network.MinecraftPacketDataSerializer;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.phys.BaseBlockPosition;
import net.alis.protocoller.samples.resources.MinecraftKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.security.PublicKey;
import java.util.*;

public interface PacketDataContainer {


    @Nullable Player getPlayer();


    MinecraftPacketType getType();

    Object getRawPacket();

    Set<Object> readSet(int index);

    boolean readBoolean(int index);

    byte readByte(int index);

    short readShort(int index);

    int readInt(int index);

    Optional<?> readOptional(int index);

    long readLong(int index);

    Enum<?> readEnum(int index);

    MinecraftPacketDataSerializer readOriginalDataSerializer(int index);

    Entity readMinecraftEntity(int index);

    PlayerAbilities readPlayerAbilities(int index);

    BlockPosition readBlockPosition(int index);

    BaseBlockPosition readBaseBlockPosition(int index);

    MinecraftKey readMinecraftKey(int index);

    ChatComponent readIChatBaseComponent(int index);

    float readFloat(int index);

    double readDouble(int index);

    boolean[] readBooleanArray(int index);

    List<?> readList(int index);

    PublicKey readPublicKey(int index);

    byte[] readByteArray(int index);

    short[] readShortArray(int index);

    int[] readIntArray(int index);

    long[] readLongArray(int index);

    float[] readFloatArray(int index);

    double[] readDoubleArray(int index);

    String[] readStringArray(int index);

    GameProfile readGameProfile(int index);

    ServerPing readServerPing(int index);

    String readString(int index);

    <T> T readObject(int index, Class<?> type);

    Enum<?> readEnumConstant(int index, Class<? extends Enum<?>> type);

    ItemStack readMinecraftItemStack(int index);

    Collection<Object> readCollection(int index);

    Map<Object, Object> readMap(int index);

    UUID readUUID(int index);



    void writeUUID(int index, UUID uuid);

    void writeSpecify(int index, Class<?> type, Object param);

    void writeMap(int index, Map<Object, Object> map);

    void writeSet(int index, Set<Object> set);

    void writeBaseBlockPosition(int index, BaseBlockPosition position);

    void writeCollection(int index, Collection<Object> collection);

    void writeMinecraftItemStack(int index, ItemStack stack);

    void writePlayerAbilities(int index, PlayerAbilities abilities);

    void writeBlockPosition(int index, BlockPosition position);

    void writeMinecraftKey(int index, MinecraftKey key);

    void writeBoolean(int index, boolean param);

    void writeByte(int index, byte param);

    void writeShort(int index, short param);

    void writeIChatBaseComponent(int index, ChatComponent component);

    void writeServerPing(int index, ServerPing param);

    void writeInt(int index, int param);

    void writeGameProfile(int index, GameProfile profile);

    void writeOptional(int index, Optional<?> param);

    void writeOriginalDataSerializer(int index, MinecraftPacketDataSerializer serializer);

    void writePublicKey(int index, PublicKey key);

    void writeList(int index, List<?> param);

    void writeLong(int index, long param);

    void writeFloat(int index, float param);

    void writeDouble(int index, double param);

    void writeString(int index, String param);

    void writeObject(int index, Object param);

    void writeBooleanArray(int index, boolean[] array);

    void writeByteArray(int index, byte[] param);

    void writeShortArray(int index, short[] param);

    void writeIntArray(int index, int[] param);

    void writeLongArray(int index, long[] param);

    void writeFloatArray(int index, float[] param);

    void writeDoubleArray(int index, double[] param);

    void writeStringArray(int index, String[] param);

    void writeEnumConstant(int index, Enum<?> enumConstant);

    void writeMinecraftEntity(int index, Entity entity);
}
