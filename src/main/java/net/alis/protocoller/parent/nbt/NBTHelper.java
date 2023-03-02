package net.alis.protocoller.parent.nbt;

import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.nbt.tags.NBTTagEnd;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTHelper {

    public static NBTTagCompound readCompressed(InputStream is) throws IOException {
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(is)));
        NBTTagCompound nbttagcompound;
        try {
            nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
        } finally {
            datainputstream.close();
        }
        return nbttagcompound;
    }

    public static void writeCompressed(NBTTagCompound compound, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)))) {
            write(compound, dataoutputstream);
        }
    }

    public static void safeWrite(NBTTagCompound compound, File fileIn) throws IOException {
        File file1 = new File(fileIn.getAbsolutePath() + "_tmp");
        if (file1.exists()) file1.delete();
        write(compound, file1);
        if (fileIn.exists()) fileIn.delete();
        if (fileIn.exists()) {
            throw new IOException("Failed to delete " + fileIn);
        } else {
            file1.renameTo(fileIn);
        }
    }

    public static void write(NBTTagCompound compound, File fileIn) throws IOException {
        try (DataOutputStream dataoutputstream = new DataOutputStream(Files.newOutputStream(fileIn.toPath()))) {
            write(compound, dataoutputstream);
        }
    }

    public static NBTTagCompound read(File fileIn) throws IOException {
        if (!fileIn.exists()) {
            return null;
        } else {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(fileIn));
            NBTTagCompound nbttagcompound;
            try {
                nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
            } finally {
                datainputstream.close();
            }
            return nbttagcompound;
        }
    }


    public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
        return read(inputStream, NBTSizeTracker.INFINITE);
    }

    public static NBTTagCompound read(DataInput input, NBTSizeTracker accounter) throws IOException {
        NBTBase nbtbase = read(input, 0, accounter);
        if (nbtbase instanceof NBTTagCompound) {
            return (NBTTagCompound)nbtbase;
        } else {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void write(NBTTagCompound compound, DataOutput output) throws IOException {
        writeTag(compound, output);
    }

    private static void writeTag(NBTBase tag, DataOutput output) throws IOException {
        output.writeByte(tag.getId());
        if (tag.getId() != 0) {
            output.writeUTF("");
            tag.write(output);
        }
    }

    private static NBTBase read(DataInput input, int depth, NBTSizeTracker accounter) throws IOException {
        byte b0 = input.readByte();
        if (b0 == 0) {
            return new NBTTagEnd();
        } else {
            input.readUTF();
            NBTBase nbtbase = NBTBase.createNewByType(b0);
            try {
                nbtbase.read(input, depth, accounter);
                return nbtbase;
            } catch (IOException ioexception) {
                throw new RuntimeException("Failed to read tag [UNNAMED TAG] with type: " + b0);
            }
        }
    }

    public static BlockPosition readBlockPosition(NBTTagCompound tag) {
        return new BlockPosition(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
    }

    public static NBTTagCompound createBlockPositionTag(BlockPosition pos) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setInteger("X", pos.getX());
        nbttagcompound.setInteger("Y", pos.getY());
        nbttagcompound.setInteger("Z", pos.getZ());
        return nbttagcompound;
    }



}
