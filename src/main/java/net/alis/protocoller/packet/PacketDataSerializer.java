package net.alis.protocoller.packet;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.network.packet.PacketTypesInitializer;
import net.alis.protocoller.parent.authlib.GameProfile;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.entity.player.PlayerAbilities;
import net.alis.protocoller.parent.network.MinecraftPacketDataSerializer;
import net.alis.protocoller.parent.network.chat.ChatComponent;
import net.alis.protocoller.parent.network.chat.ChatSerializer;
import net.alis.protocoller.parent.network.status.ServerPing;
import net.alis.protocoller.parent.phys.BaseBlockPosition;
import net.alis.protocoller.parent.resources.MinecraftKey;
import net.alis.protocoller.util.ObjectAccessor;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

public class PacketDataSerializer extends ObjectAccessor implements Cloneable {

    private final MinecraftPacketType packetType;

    public PacketDataSerializer(Object rawPacket) {
        super(rawPacket);
        this.packetType = PacketTypesInitializer.setPacketType(rawPacket);
    }

    public PacketDataSerializer(PacketDataSerializer serializer) {
        super(new ObjectAccessor(serializer.getObject()).getObject());
        packetType = serializer.packetType;
    }

    public MinecraftPacketType getType() {
        return packetType;
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

    public PlayerAbilities readPlayerAbilities(int index) {
        return new PlayerAbilities(super.read(index, ClassesContainer.INSTANCE.getPlayerAbilitiesClass()));
    }

    public BlockPosition readBlockPosition(int index) {
        return new BlockPosition(new BaseBlockPosition(super.read(index, ClassesContainer.INSTANCE.getBlockPositionClass())));
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


    private void writePlayerAbilities(int index, PlayerAbilities abilities) {
        super.write(index, abilities.createOriginal());
    }
    public void writeBlockPosition(int index, BlockPosition position) {
        super.write(index, position.createOriginal());
    }
    public void writeMinecraftKey(int index, MinecraftKey key) {
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

    public void writeIChatBaseComponent(int index, ChatComponent component) {
        super.writeSpecify(index, ClassesContainer.INSTANCE.getIChatBaseComponentClass(), component.createIChatBaseComponent());
    }

    public void writeServerPing(int index, ServerPing param) {
        super.write(index, param.createOriginal());
    }

    public void writeInt(int index, int param) {
        super.write(index, param);
    }
    public void writeGameProfile(int index, GameProfile profile) {
        super.write(index, profile.createOriginal());
    }
    public void writeOptional(int index, Optional<?> param) {
        super.write(index, param);
    }
    public void writeOriginalDataSerializer(int index, MinecraftPacketDataSerializer serializer) {
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
