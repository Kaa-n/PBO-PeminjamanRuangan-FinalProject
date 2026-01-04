package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Admin;
import org.pbopeminjamanruanganjavafx.model.Peminjam;
import org.pbopeminjamanruanganjavafx.model.User;
import org.pbopeminjamanruanganjavafx.util.HashSHA;

public class UserDAO {

    public User validasiLogin(String username_email, String rawPassword) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM user WHERE (username = ? OR email = ?) AND password = ?"; // menancari data user berdasarkan username/email dan password
        
        try {
            // Hash Password Input User
            String passwordHash = HashSHA.konversiHexString(HashSHA.konversiSHA(rawPassword));

            PreparedStatement ps = conn.prepareStatement(sql); // proses pembuatan statement
            ps.setString(1, username_email); // ? pertama adalah username
            ps.setString(2, username_email); // ? kedua adalah email
            ps.setString(3, passwordHash); // ? ketiga adalah password yang sudah di-hash

            ResultSet rs = ps.executeQuery(); // eksekusi query

            if (rs.next()) { // jika ada data yang ditemukan
                // Ambil data dasar
                int id = rs.getInt("id_user");
                String role = rs.getString("role");
                String dbUsername = rs.getString("username");

                // POLYMORPHISM: Return tipe User, isi berbeda tergantung role
                if ("admin".equalsIgnoreCase(role) || "super_admin".equalsIgnoreCase(role)) {
                    return new Admin(id, dbUsername, role);
                } else {
                    return new Peminjam(id, dbUsername, role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Login gagal
    }
}