package ru.shtyrev;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

public class RSAEncryptor {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Размер ключа 2048 бит
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encryptMessage(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    public static String decryptMessage(byte[] encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSAEncryptor.generateKeyPair();

        byte[] encrypted = RSAEncryptor.encryptMessage("ahahhahahhaha", keyPair.getPublic());
        System.out.println(Arrays.toString(encrypted));


        String decrypted = RSAEncryptor.decryptMessage(encrypted, keyPair.getPrivate());
        System.out.println(decrypted);
    }
}
