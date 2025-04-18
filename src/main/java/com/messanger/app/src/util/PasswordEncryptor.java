package com.messanger.app.src.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordEncryptor {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "1234567890123456";

    public static String encode(String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(),ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while encoding password", e);
        }
    }

    public static String decode(String encryptedPassword) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(),ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while decoding password",e);
        }
    }


    public static void main(String[] args) {
        String pass = "admin1234";
        System.out.println(PasswordEncryptor.encode(pass));
        System.out.println(PasswordEncryptor.decode(PasswordEncryptor.encode(pass)));
    }

}
