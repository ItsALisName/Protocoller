package net.alis.protocoller.samples.authlib.properties;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.Array;
import java.security.*;

public class Property implements ObjectSample {

    public static Class<?> PROPERTY_ARRAY_CLASS = Array.newInstance(ClassAccessor.get().getPropertyClass(), 1).getClass();

    private final String name;
    private final String value;
    private final String signature;

    public Property(String value, String name) {
        this(value, name, null);
    }

    public Property(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public Property(Object property) {
        AccessedObject objectAccessor = new AccessedObject(property);
        this.name = objectAccessor.read(0, String.class);
        this.value = objectAccessor.read(1, String.class);
        if(objectAccessor.read(2, String.class) != null) {
            this.signature = objectAccessor.read(2, String.class);
        } else {
            this.signature = null;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getSignature() {
        return this.signature;
    }

    public boolean hasSignature() {
        return this.signature != null;
    }

    public boolean isSignatureValid(PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signature.update(this.value.getBytes());
            return signature.verify(Base64.decodeBase64(this.signature));
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException var3) {
            var3.printStackTrace();
        }

        return false;
    }

    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getPropertyClass(), String.class, String.class, String.class),
                this.name, this.value, this.signature
        );
    }

}
