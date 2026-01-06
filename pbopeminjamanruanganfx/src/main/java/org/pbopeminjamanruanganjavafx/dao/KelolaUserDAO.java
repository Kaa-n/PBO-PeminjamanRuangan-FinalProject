package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.util.HashSHA;

public class KelolaUserDAO {

    private final Connection conn = DatabaseConnection.getConnection();
    public boolean isUsernameOrEmailExist(String username, String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ? OR email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public int insertUser(String nama, String username, String email, String role) throws Exception {
        String sql = "INSERT INTO user (nama, username, email, password, role) VALUES (?, ?, ?, ?, ?)";
        String passwordHash = HashSHA.konversiHexString(
                HashSHA.konversiSHA("12345678")
        );

        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, nama);
        ps.setString(2, username);
        ps.setString(3, email);
        ps.setString(4, passwordHash);
        ps.setString(5, role);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
        return -1;
    }
    public void insertAdmin(int idUser, String level) throws Exception {
        String sql = "INSERT INTO admin (id_user, level) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idUser);
        ps.setString(2, level);
        ps.executeUpdate();
    }
    public void insertPeminjam(int idUser, String nim, String prodi) throws Exception {
        String sql = "INSERT INTO peminjam (id_user, nomor_induk, prodi) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idUser);
        ps.setString(2, nim);
        ps.setString(3, prodi);
        ps.executeUpdate();
    }
    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM user";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}