package net.alis.protocoller.samples.nbt;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.samples.nbt.tags.NBTTagEnd;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;

public class CompressedStreamTools {

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

    public static void safeWrite(NBTTagCompound compound, @NotNull File fileIn) throws IOException {
        File file1 = new File(fileIn.getAbsolutePath() + "_tmp");
        if (file1.exists()) {
            file1.delete();
        }
        write(compound, file1);
        if (fileIn.exists()) {
            fileIn.delete();
        }
        if (fileIn.exists()) {
            new ExceptionBuilder().getNBTExceptions().fileRemoveError(fileIn).throwException();
        } else {
            file1.renameTo(fileIn);
        }
    }

    public static void write(NBTTagCompound compound, @NotNull File fileIn) throws IOException {
        try (DataOutputStream dataoutputstream = new DataOutputStream(Files.newOutputStream(fileIn.toPath()))) {
            write(compound, dataoutputstream);
        }
    }

    @Nullable
    public static NBTTagCompound read(@NotNull File fileIn) throws IOException {
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

    public static @NotNull NBTTagCompound read(DataInputStream inputStream) throws IOException {
        return read(inputStream, NBTSizeTracker.INFINITE);
    }

    public static @NotNull NBTTagCompound read(DataInput input, NBTSizeTracker accounter) throws IOException {
        NBTBase nbtbase = read(input, 0, accounter);
        if (nbtbase instanceof NBTTagCompound) {
            return (NBTTagCompound)nbtbase;
        } else {
            return new ExceptionBuilder().getNBTExceptions().customMessage("Root tag must be Loot named compound tag").throwException();
        }
    }

    public static void write(NBTTagCompound compound, DataOutput output) throws IOException {
        writeTag(compound, output);
    }

    private static void writeTag(@NotNull NBTBase tag, @NotNull DataOutput output) throws IOException {
        output.writeByte(tag.getId());
        if (tag.getId() != 0) {
            output.writeUTF("");
            tag.write(output);
        }
    }

    private static NBTBase read(@NotNull DataInput input, int depth, NBTSizeTracker accounter) throws IOException {
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
                return new ExceptionBuilder().getNBTExceptions().defineReason(ioexception).byteReadError(b0).throwException();
            }
        }
    }
}
