package net.alis.protocoller.parent.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.nbt.NBTHelper;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import net.alis.protocoller.parent.nbt.NBTTagCompound;
import net.alis.protocoller.parent.network.chat.ChatComponent;
import net.alis.protocoller.util.ObjectAccessor;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class MinecraftPacketDataSerializer extends ByteBuf {
    
    private final ByteBuf byteBuf;

    public MinecraftPacketDataSerializer(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }
    
    public MinecraftPacketDataSerializer(Object originalSerializer) {
        this.byteBuf = new ObjectAccessor(originalSerializer).read(0, ByteBuf.class);
    }

    public static int getVarIntSize(int input) {
        for (int i = 1; i < 5; ++i) {
            if ((input & -1 << i * 7) == 0) {
                return i;
            }
        }
        return 5;
    }

    public MinecraftPacketDataSerializer writeByteArray(byte[] array) {
        this.writeVarIntToBuffer(array.length);
        this.writeBytes(array);
        return this;
    }

    public byte[] readByteArray() {
        return this.readByteArray(this.readableBytes());
    }

    public byte[] readByteArray(int maxLength) {
        int i = this.readVarIntFromBuffer();
        if (i > maxLength) {
            throw new DecoderException("ByteArray with size " + i + " is bigger than allowed " + maxLength);
        } else {
            byte[] abyte = new byte[i];
            this.readBytes(abyte);
            return abyte;
        }
    }
    
    public MinecraftPacketDataSerializer writeVarIntArray(int[] array) {
        this.writeVarIntToBuffer(array.length);
        for (int i : array) {
            this.writeVarIntToBuffer(i);
        }
        return this;
    }

    public int[] readVarIntArray() {
        return this.readVarIntArray(this.readableBytes());
    }

    public int[] readVarIntArray(int maxLength) {
        int i = this.readVarIntFromBuffer();
        if (i > maxLength) {
            throw new DecoderException("VarIntArray with size " + i + " is bigger than allowed " + maxLength);
        } else {
            int[] aint = new int[i];
            for (int j = 0; j < aint.length; ++j) {
                aint[j] = this.readVarIntFromBuffer();
            }
            return aint;
        }
    }

    public MinecraftPacketDataSerializer writeLongArray(long[] array) {
        this.writeVarIntToBuffer(array.length);
        for (long i : array) {
            this.writeLong(i);
        }
        return this;
    }

    public long[] readLongArray(@Nullable long[] array) {
        return this.readLongArray(array, this.readableBytes() / 8);
    }

    public long[] readLongArray(@Nullable long[] longs, int int0) {
        int i = this.readVarIntFromBuffer();
        if (longs == null || longs.length != i) {
            if (i > int0) {
                throw new DecoderException("LongArray with size " + i + " is bigger than allowed " + int0);
            }
            longs = new long[i];
        }
        for (int j = 0; j < longs.length; ++j) {
            longs[j] = this.readLong();
        }

        return longs;
    }

    public BlockPosition readBlockPos() {
        return BlockPosition.fromLong(this.readLong());
    }

    public MinecraftPacketDataSerializer writeBlockPos(BlockPosition pos) {
        this.writeLong(pos.toLong());
        return this;
    }

    public ChatComponent readChatComponent() throws IOException {
        return new ChatComponent(this.readStringFromBuffer(32767));
    }

    public MinecraftPacketDataSerializer writeTextComponent(ChatComponent component) {
        return this.writeString(component.getString());
    }

    public <T extends Enum<T>> T readEnumValue(Class<T> enumClass) {
        return (T)((Enum[])enumClass.getEnumConstants())[this.readVarIntFromBuffer()];
    }

    public MinecraftPacketDataSerializer writeEnumValue(Enum<?> value) {
        return this.writeVarIntToBuffer(value.ordinal());
    }

    public int readVarIntFromBuffer() {
        int i = 0,j = 0;
        while (true) {
            byte b0 = this.readByte();
            i |= (b0 & 127) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
            if ((b0 & 128) != 128) break;
        }
        return i;
    }

    public long readVarLong() {
        long i = 0L; int j = 0;
        while (true) {
            byte b0 = this.readByte();
            i |= (long)(b0 & 127) << j++ * 7;
            if (j > 10) throw new RuntimeException("VarLong too big");
            if ((b0 & 128) != 128) break;
        }

        return i;
    }

    public MinecraftPacketDataSerializer writeUuid(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
        return this;
    }

    public UUID readUuid() {
        return new UUID(this.readLong(), this.readLong());
    }

    public MinecraftPacketDataSerializer writeVarIntToBuffer(int input) {
        while ((input & -128) != 0) {
            this.writeByte(input & 127 | 128);
            input >>>= 7;
        }
        this.writeByte(input);
        return this;
    }

    public MinecraftPacketDataSerializer writeVarLong(long value) {
        while ((value & -128L) != 0L) {
            this.writeByte((int)(value & 127L) | 128);
            value >>>= 7;
        }
        this.writeByte((int)value);
        return this;
    }

    public MinecraftPacketDataSerializer writeNBTTagCompoundToBuffer(@Nullable NBTTagCompound nbt) {
        if (nbt == null) {
            this.writeByte(0);
        } else {
            try {
                NBTHelper.write(nbt, new ByteBufOutputStream(this));
            }
            catch (IOException ioexception)
            {
                throw new EncoderException(ioexception);
            }
        }
        return this;
    }

    @Nullable
    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        int i = this.readerIndex();
        byte b0 = this.readByte();
        if (b0 == 0) {
            return null;
        } else {
            this.readerIndex(i);
            try {
                return NBTHelper.read(new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
            } catch (IOException ioexception) {
                throw new EncoderException(ioexception);
            }
        }
    }

    public String readStringFromBuffer(int maxLength) {
        int i = this.readVarIntFromBuffer();
        if (i > maxLength * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")");
        } else if (i < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String s = new String(this.readBytes(i).array(), Charsets.UTF_8);
            if (s.length() > maxLength) {
                throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
            } else {
                return s;
            }
        }
    }

    public MinecraftPacketDataSerializer writeString(String string) {
        byte[] bytes = string.getBytes(Charsets.UTF_8);
        if (bytes.length > 32767) {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
        } else {
            this.writeVarIntToBuffer(bytes.length);
            this.writeBytes(bytes);
            return this;
        }
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public int capacity() {
        return this.byteBuf.capacity();
    }

    public ByteBuf capacity(int i) {
        return this.byteBuf.capacity(i);
    }

    public int maxCapacity() {
        return this.byteBuf.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.byteBuf.alloc();
    }

    public ByteOrder order() {
        return this.byteBuf.order();
    }

    public ByteBuf order(ByteOrder byteorder) {
        return this.byteBuf.order(byteorder);
    }

    public ByteBuf unwrap() {
        return this.byteBuf.unwrap();
    }

    public boolean isDirect() {
        return this.byteBuf.isDirect();
    }

    public boolean isReadOnly() {
        return this.byteBuf.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return this.byteBuf.asReadOnly();
    }

    public int readerIndex() {
        return this.byteBuf.readerIndex();
    }

    public ByteBuf readerIndex(int i) {
        return this.byteBuf.readerIndex(i);
    }

    public int writerIndex() {
        return this.byteBuf.writerIndex();
    }

    public ByteBuf writerIndex(int i) {
        return this.byteBuf.writerIndex(i);
    }

    public ByteBuf setIndex(int i, int j) {
        return this.byteBuf.setIndex(i, j);
    }

    public int readableBytes() {
        return this.byteBuf.readableBytes();
    }

    public int writableBytes() {
        return this.byteBuf.writableBytes();
    }

    public int maxWritableBytes() {
        return this.byteBuf.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.byteBuf.isReadable();
    }

    public boolean isReadable(int i) {
        return this.byteBuf.isReadable(i);
    }

    public boolean isWritable() {
        return this.byteBuf.isWritable();
    }

    public boolean isWritable(int i) {
        return this.byteBuf.isWritable(i);
    }

    public ByteBuf clear() {
        return this.byteBuf.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.byteBuf.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.byteBuf.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.byteBuf.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.byteBuf.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.byteBuf.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.byteBuf.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int i) {
        return this.byteBuf.ensureWritable(i);
    }

    public int ensureWritable(int i, boolean flag) {
        return this.byteBuf.ensureWritable(i, flag);
    }

    public boolean getBoolean(int i) {
        return this.byteBuf.getBoolean(i);
    }

    public byte getByte(int i) {
        return this.byteBuf.getByte(i);
    }

    public short getUnsignedByte(int i) {
        return this.byteBuf.getUnsignedByte(i);
    }

    public short getShort(int i) {
        return this.byteBuf.getShort(i);
    }

    public short getShortLE(int i) {
        return this.byteBuf.getShortLE(i);
    }

    public int getUnsignedShort(int i) {
        return this.byteBuf.getUnsignedShort(i);
    }

    public int getUnsignedShortLE(int i) {
        return this.byteBuf.getUnsignedShortLE(i);
    }

    public int getMedium(int i) {
        return this.byteBuf.getMedium(i);
    }

    public int getMediumLE(int i) {
        return this.byteBuf.getMediumLE(i);
    }

    public int getUnsignedMedium(int i) {
        return this.byteBuf.getUnsignedMedium(i);
    }

    public int getUnsignedMediumLE(int i) {
        return this.byteBuf.getUnsignedMediumLE(i);
    }

    public int getInt(int i) {
        return this.byteBuf.getInt(i);
    }

    public int getIntLE(int i) {
        return this.byteBuf.getIntLE(i);
    }

    public long getUnsignedInt(int i) {
        return this.byteBuf.getUnsignedInt(i);
    }

    public long getUnsignedIntLE(int i) {
        return this.byteBuf.getUnsignedIntLE(i);
    }

    public long getLong(int i) {
        return this.byteBuf.getLong(i);
    }

    public long getLongLE(int i) {
        return this.byteBuf.getLongLE(i);
    }

    public char getChar(int i) {
        return this.byteBuf.getChar(i);
    }

    public float getFloat(int i) {
        return this.byteBuf.getFloat(i);
    }

    public double getDouble(int i) {
        return this.byteBuf.getDouble(i);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf) {
        return this.byteBuf.getBytes(i, bytebuf);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
        return this.byteBuf.getBytes(i, bytebuf, j);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.byteBuf.getBytes(i, bytebuf, j, k);
    }

    public ByteBuf getBytes(int i, byte[] abyte) {
        return this.byteBuf.getBytes(i, abyte);
    }

    public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
        return this.byteBuf.getBytes(i, abyte, j, k);
    }

    public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
        return this.byteBuf.getBytes(i, bytebuffer);
    }

    public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
        return this.byteBuf.getBytes(i, outputstream, j);
    }

    public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
        return this.byteBuf.getBytes(i, gatheringbytechannel, j);
    }

    public int getBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
        return this.byteBuf.getBytes(i, filechannel, j, k);
    }

    public CharSequence getCharSequence(int i, int j, Charset charset) {
        return this.byteBuf.getCharSequence(i, j, charset);
    }

    public ByteBuf setBoolean(int i, boolean flag) {
        return this.byteBuf.setBoolean(i, flag);
    }

    public ByteBuf setByte(int i, int j) {
        return this.byteBuf.setByte(i, j);
    }

    public ByteBuf setShort(int i, int j) {
        return this.byteBuf.setShort(i, j);
    }

    public ByteBuf setShortLE(int i, int j) {
        return this.byteBuf.setShortLE(i, j);
    }

    public ByteBuf setMedium(int i, int j) {
        return this.byteBuf.setMedium(i, j);
    }

    public ByteBuf setMediumLE(int i, int j) {
        return this.byteBuf.setMediumLE(i, j);
    }

    public ByteBuf setInt(int i, int j) {
        return this.byteBuf.setInt(i, j);
    }

    public ByteBuf setIntLE(int i, int j) {
        return this.byteBuf.setIntLE(i, j);
    }

    public ByteBuf setLong(int i, long j) {
        return this.byteBuf.setLong(i, j);
    }

    public ByteBuf setLongLE(int i, long j) {
        return this.byteBuf.setLongLE(i, j);
    }

    public ByteBuf setChar(int i, int j) {
        return this.byteBuf.setChar(i, j);
    }

    public ByteBuf setFloat(int i, float f) {
        return this.byteBuf.setFloat(i, f);
    }

    public ByteBuf setDouble(int i, double d0) {
        return this.byteBuf.setDouble(i, d0);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf) {
        return this.byteBuf.setBytes(i, bytebuf);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
        return this.byteBuf.setBytes(i, bytebuf, j);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.byteBuf.setBytes(i, bytebuf, j, k);
    }

    public ByteBuf setBytes(int i, byte[] abyte) {
        return this.byteBuf.setBytes(i, abyte);
    }

    public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
        return this.byteBuf.setBytes(i, abyte, j, k);
    }

    public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
        return this.byteBuf.setBytes(i, bytebuffer);
    }

    public int setBytes(int i, InputStream inputstream, int j) throws IOException {
        return this.byteBuf.setBytes(i, inputstream, j);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
        return this.byteBuf.setBytes(i, scatteringbytechannel, j);
    }

    public int setBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
        return this.byteBuf.setBytes(i, filechannel, j, k);
    }

    public ByteBuf setZero(int i, int j) {
        return this.byteBuf.setZero(i, j);
    }

    public int setCharSequence(int i, CharSequence charsequence, Charset charset) {
        return this.byteBuf.setCharSequence(i, charsequence, charset);
    }

    public boolean readBoolean() {
        return this.byteBuf.readBoolean();
    }

    public byte readByte() {
        return this.byteBuf.readByte();
    }

    public short readUnsignedByte() {
        return this.byteBuf.readUnsignedByte();
    }

    public short readShort() {
        return this.byteBuf.readShort();
    }

    public short readShortLE() {
        return this.byteBuf.readShortLE();
    }

    public int readUnsignedShort() {
        return this.byteBuf.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.byteBuf.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.byteBuf.readMedium();
    }

    public int readMediumLE() {
        return this.byteBuf.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.byteBuf.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.byteBuf.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.byteBuf.readInt();
    }

    public int readIntLE() {
        return this.byteBuf.readIntLE();
    }

    public long readUnsignedInt() {
        return this.byteBuf.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.byteBuf.readUnsignedIntLE();
    }

    public long readLong() {
        return this.byteBuf.readLong();
    }

    public long readLongLE() {
        return this.byteBuf.readLongLE();
    }

    public char readChar() {
        return this.byteBuf.readChar();
    }

    public float readFloat() {
        return this.byteBuf.readFloat();
    }

    public double readDouble() {
        return this.byteBuf.readDouble();
    }

    public ByteBuf readBytes(int i) {
        return this.byteBuf.readBytes(i);
    }

    public ByteBuf readSlice(int i) {
        return this.byteBuf.readSlice(i);
    }

    public ByteBuf readRetainedSlice(int i) {
        return this.byteBuf.readRetainedSlice(i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf) {
        return this.byteBuf.readBytes(bytebuf);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i) {
        return this.byteBuf.readBytes(bytebuf, i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
        return this.byteBuf.readBytes(bytebuf, i, j);
    }

    public ByteBuf readBytes(byte[] abyte) {
        return this.byteBuf.readBytes(abyte);
    }

    public ByteBuf readBytes(byte[] abyte, int i, int j) {
        return this.byteBuf.readBytes(abyte, i, j);
    }

    public ByteBuf readBytes(ByteBuffer bytebuffer) {
        return this.byteBuf.readBytes(bytebuffer);
    }

    public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
        return this.byteBuf.readBytes(outputstream, i);
    }

    public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
        return this.byteBuf.readBytes(gatheringbytechannel, i);
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        return this.byteBuf.readCharSequence(i, charset);
    }

    public int readBytes(FileChannel filechannel, long i, int j) throws IOException {
        return this.byteBuf.readBytes(filechannel, i, j);
    }

    public ByteBuf skipBytes(int i) {
        return this.byteBuf.skipBytes(i);
    }

    public ByteBuf writeBoolean(boolean flag) {
        return this.byteBuf.writeBoolean(flag);
    }

    public ByteBuf writeByte(int i) {
        return this.byteBuf.writeByte(i);
    }

    public ByteBuf writeShort(int i) {
        return this.byteBuf.writeShort(i);
    }

    public ByteBuf writeShortLE(int i) {
        return this.byteBuf.writeShortLE(i);
    }

    public ByteBuf writeMedium(int i) {
        return this.byteBuf.writeMedium(i);
    }

    public ByteBuf writeMediumLE(int i) {
        return this.byteBuf.writeMediumLE(i);
    }

    public ByteBuf writeInt(int i) {
        return this.byteBuf.writeInt(i);
    }

    public ByteBuf writeIntLE(int i) {
        return this.byteBuf.writeIntLE(i);
    }

    public ByteBuf writeLong(long i) {
        return this.byteBuf.writeLong(i);
    }

    public ByteBuf writeLongLE(long i) {
        return this.byteBuf.writeLongLE(i);
    }

    public ByteBuf writeChar(int i) {
        return this.byteBuf.writeChar(i);
    }

    public ByteBuf writeFloat(float f) {
        return this.byteBuf.writeFloat(f);
    }

    public ByteBuf writeDouble(double d0) {
        return this.byteBuf.writeDouble(d0);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf) {
        return this.byteBuf.writeBytes(bytebuf);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
        return this.byteBuf.writeBytes(bytebuf, i);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
        return this.byteBuf.writeBytes(bytebuf, i, j);
    }

    public ByteBuf writeBytes(byte[] abyte) {
        return this.byteBuf.writeBytes(abyte);
    }

    public ByteBuf writeBytes(byte[] abyte, int i, int j) {
        return this.byteBuf.writeBytes(abyte, i, j);
    }

    public ByteBuf writeBytes(ByteBuffer bytebuffer) {
        return this.byteBuf.writeBytes(bytebuffer);
    }

    public int writeBytes(InputStream inputstream, int i) throws IOException {
        return this.byteBuf.writeBytes(inputstream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
        return this.byteBuf.writeBytes(scatteringbytechannel, i);
    }

    public int writeBytes(FileChannel filechannel, long i, int j) throws IOException {
        return this.byteBuf.writeBytes(filechannel, i, j);
    }

    public ByteBuf writeZero(int i) {
        return this.byteBuf.writeZero(i);
    }

    public int writeCharSequence(CharSequence charsequence, Charset charset) {
        return this.byteBuf.writeCharSequence(charsequence, charset);
    }

    public int indexOf(int i, int j, byte b0) {
        return this.byteBuf.indexOf(i, j, b0);
    }

    public int bytesBefore(byte b0) {
        return this.byteBuf.bytesBefore(b0);
    }

    public int bytesBefore(int i, byte b0) {
        return this.byteBuf.bytesBefore(i, b0);
    }

    public int bytesBefore(int i, int j, byte b0) {
        return this.byteBuf.bytesBefore(i, j, b0);
    }

    public int forEachByte(ByteProcessor byteprocessor) {
        return this.byteBuf.forEachByte(byteprocessor);
    }

    public int forEachByte(int i, int j, ByteProcessor byteprocessor) {
        return this.byteBuf.forEachByte(i, j, byteprocessor);
    }

    public int forEachByteDesc(ByteProcessor byteprocessor) {
        return this.byteBuf.forEachByteDesc(byteprocessor);
    }

    public int forEachByteDesc(int i, int j, ByteProcessor byteprocessor) {
        return this.byteBuf.forEachByteDesc(i, j, byteprocessor);
    }

    public ByteBuf copy() {
        return this.byteBuf.copy();
    }

    public ByteBuf copy(int i, int j) {
        return this.byteBuf.copy(i, j);
    }

    public ByteBuf slice() {
        return this.byteBuf.slice();
    }

    public ByteBuf retainedSlice() {
        return this.byteBuf.retainedSlice();
    }

    public ByteBuf slice(int i, int j) {
        return this.byteBuf.slice(i, j);
    }

    public ByteBuf retainedSlice(int i, int j) {
        return this.byteBuf.retainedSlice(i, j);
    }

    public ByteBuf duplicate() {
        return this.byteBuf.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.byteBuf.retainedDuplicate();
    }

    public int nioBufferCount() {
        return this.byteBuf.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.byteBuf.nioBuffer();
    }

    public ByteBuffer nioBuffer(int i, int j) {
        return this.byteBuf.nioBuffer(i, j);
    }

    public ByteBuffer internalNioBuffer(int i, int j) {
        return this.byteBuf.internalNioBuffer(i, j);
    }

    public ByteBuffer[] nioBuffers() {
        return this.byteBuf.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int i, int j) {
        return this.byteBuf.nioBuffers(i, j);
    }

    public boolean hasArray() {
        return this.byteBuf.hasArray();
    }

    public byte[] array() {
        return this.byteBuf.array();
    }

    public int arrayOffset() {
        return this.byteBuf.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.byteBuf.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.byteBuf.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.byteBuf.toString(charset);
    }

    public String toString(int i, int j, Charset charset) {
        return this.byteBuf.toString(i, j, charset);
    }

    public int hashCode() {
        return this.byteBuf.hashCode();
    }

    public boolean equals(Object object) {
        return this.byteBuf.equals(object);
    }

    public int compareTo(ByteBuf bytebuf) {
        return this.byteBuf.compareTo(bytebuf);
    }

    public String toString() {
        return this.byteBuf.toString();
    }

    public ByteBuf retain(int i) {
        return this.byteBuf.retain(i);
    }

    public ByteBuf retain() {
        return this.byteBuf.retain();
    }

    public ByteBuf touch() {
        return this.byteBuf.touch();
    }

    public ByteBuf touch(Object object) {
        return this.byteBuf.touch(object);
    }

    public int refCnt() {
        return this.byteBuf.refCnt();
    }

    public boolean release() {
        return this.byteBuf.release();
    }

    public boolean release(int i) {
        return this.byteBuf.release(i);
    }

    public <T> Optional<T> read(MinecraftPacketDataSerializer.Reader<T> reader) {
        return this.readBoolean() ? Optional.of(reader.apply(this)) : Optional.empty();
    }

    @Nullable
    public <T> T read$0(MinecraftPacketDataSerializer.Reader<T> reader) {
        return this.readBoolean() ? reader.apply(this) : null;
    }

    @FunctionalInterface
    public interface Reader<T> extends Function<MinecraftPacketDataSerializer, T> {
        default MinecraftPacketDataSerializer.Reader<Optional<T>> asOptional() {
            return (packetdataserializer) -> {
                return packetdataserializer.read(this);
            };
        }
    }

    public Object createOriginal() {
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getPacketDataSerializerClass(), ByteBuf.class),
                this.byteBuf
        );
    }
}
