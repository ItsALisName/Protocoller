package net.alis.protocoller.samples;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MinecraftEncryption {

    public static SecretKey getSecretKey() throws Exception {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public static KeyPair getKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public static byte[] getBytes(String s, PublicKey publicKey, SecretKey secretKey) throws Exception {
        try {
            return getBytes(s.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    private static byte[] getBytes(byte[] @NotNull ... bytes) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[][] bytes1 = bytes;
        int i1 = bytes.length;

        for(int i = 0; i < i1; ++i) {
            byte[] bytes2 = bytes1[i];
            messageDigest.update(bytes2);
        }

        return messageDigest.digest();
    }

    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        try {
            EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(encodedKeySpec);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull SecretKey getSecretKey(PrivateKey privateKey, byte[] bytes) throws Exception {
        byte[] bytes1 = readBytes$1(privateKey, bytes);

        try {
            return new SecretKeySpec(bytes1, "AES");
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    public static byte[] readBytes(Key key, byte[] bytes) throws Exception {
        return readBytes$2(1, key, bytes);
    }

    public static byte[] readBytes$1(Key key, byte[] bytes) throws Exception {
        return readBytes$2(2, key, bytes);
    }

    private static byte[] readBytes$2(int i, Key key, byte[] bytes) throws Exception {
        try {
            return getCipher(i, key.getAlgorithm(), key).doFinal(bytes);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    private static @NotNull Cipher getCipher(int i, String s, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(s);
        cipher.init(i, key);
        return cipher;
    }

    public static @NotNull Cipher getCipher(int i, Key key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(i, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

}
