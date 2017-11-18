package com.ape.saletracker;

import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAHelper {
    private static final String ALGO = "RSA";
    private static final int BASE64_FLAGS = 11;
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    public static PublicKey getPublicKey(String key) throws Exception {
        return KeyFactory.getInstance(ALGO).generatePublic(new X509EncodedKeySpec(Base64.decode(key, 11)));
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        return KeyFactory.getInstance(ALGO).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(key, 11)));
    }

    public static String getKeyString(Key key) throws Exception {
        return Base64.encodeToString(key.getEncoded(), 11);
    }

    public static String encrypt(String key, String cleartext) throws Exception {
        PublicKey pubKey = getPublicKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(1, pubKey);
        return Base64.encodeToString(cipher.doFinal(cleartext.getBytes("utf-8")), 11);
    }

    public static String decrypt(String key, String encrypted) throws Exception {
        PrivateKey prvKey = getPrivateKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(2, prvKey);
        return new String(cipher.doFinal(Base64.decode(encrypted, 11)));
    }

    public static String[] genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGO);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new String[]{getKeyString(publicKey), getKeyString(privateKey)};
    }
}
