package net.alis.protocoller.samples.server.rcon;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RemoteStatusReply implements ObjectSample {

    private final ByteArrayOutputStream outputStream;
    private final DataOutputStream dataOutputStream;

    public RemoteStatusReply(Object remStRp) {
        AccessedObject object = new AccessedObject(remStRp);
        this.outputStream = object.read(0, ByteArrayOutputStream.class);
        this.dataOutputStream = object.read(0, DataOutputStream.class);
    }

    public RemoteStatusReply(int size) {
        this.outputStream = new ByteArrayOutputStream(size);
        this.dataOutputStream = new DataOutputStream(this.outputStream);
    }

    public void writeBytes(byte[] bytes) throws IOException {
        this.dataOutputStream.write(bytes, 0, bytes.length);
    }

    public void writeString(String s) throws IOException {
        this.dataOutputStream.writeBytes(s);
        this.dataOutputStream.write(0);
    }

    public void write(int i) throws IOException {
        this.dataOutputStream.write(i);
    }

    public void writeShort(short s) throws IOException {
        this.dataOutputStream.writeShort(Short.reverseBytes(s));
    }

    public void writeInt(int i) throws IOException {
        this.dataOutputStream.writeInt(Integer.reverseBytes(i));
    }

    public void writeFloat(float f) throws IOException {
        this.dataOutputStream.writeInt(Integer.reverseBytes(Float.floatToIntBits(f)));
    }

    public byte[] toByteArray() {
        return this.outputStream.toByteArray();
    }

    public void reset() {
        this.outputStream.reset();
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getRemoteStatusReplyClass(), Integer.TYPE),
                this.outputStream.size()
        );
    }
}
