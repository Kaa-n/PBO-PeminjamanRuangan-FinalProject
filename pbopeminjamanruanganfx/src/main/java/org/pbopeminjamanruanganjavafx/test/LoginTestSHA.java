package org.pbopeminjamanruanganjavafx.test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LoginTestSHA {
    private static String hash_password = "";
    private static final String hash_target = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"; // Hash SHA-256 dari "password"
    // private static boolean found = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            boolean valid_password = false;
            // Input Password
            String password = "";
            do {
                System.out.print("Masukkan Password lebih dari 8 karakter: ");
                password = scanner.nextLine().trim();
                if (password.length() >= 8) {
                break;
                }
            } while(true);

            hash_password = toHexString(getSHA(password));

            System.out.print("Password: " + hash_password);
        
        
            if (hash_password.equals(hash_target)) {
                valid_password = true;
            }
            
            if (valid_password) {
                System.out.println("\nPassword valid. Akses diberikan.");
            } else {
                System.out.println("\nPassword tidak valid. Akses ditolak.");
            }

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

        // Fungsi untuk menghitung digest SHA-256 dalam bentuk byte array
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called to calculate message digest of an input and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    // Fungsi untuk mengubah byte array menjadi Hex String 64 karakter
    public static String toHexString(byte[] hash) {
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
}
