package org.pbopeminjamanruanganjavafx.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSHA {
    public static byte[] konversiSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called to calculate message digest of an input and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    // Fungsi untuk mengubah byte array menjadi Hex String 64 karakter
    public static String konversiHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros (agar panjang tetap 64 karakter)
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    // Tambahkan ini di dalam class HashSHA
    public static String hash(String input) {
        try {
            byte[] hashByte = konversiSHA(input);
            return konversiHexString(hashByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}