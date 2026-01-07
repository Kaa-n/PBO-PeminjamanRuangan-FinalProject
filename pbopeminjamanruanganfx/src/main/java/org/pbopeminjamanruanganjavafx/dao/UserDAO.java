package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Admin;
import org.pbopeminjamanruanganjavafx.model.Peminjam;
import org.pbopeminjamanruanganjavafx.model.User;
import org.pbopeminjamanruanganjavafx.util.HashSHA;
import org.pbopeminjamanruanganjavafx.util.UserSession;

public class UserDAO {

    public User validasiLogin(String username_email, String rawPassword) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM user WHERE (username = ? OR email = ?) AND password = ?";

        try {
            String passwordHash = HashSHA.konversiHexString(
                    HashSHA.konversiSHA(rawPassword)
            );

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username_email);
            ps.setString(2, username_email);
            ps.setString(3, passwordHash);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("id_user");
                String role = rs.getString("role");
                String username = rs.getString("username");

                // ===== ADMIN =====
                if ("admin".equalsIgnoreCase(role)) {
                    String sqlAdmin = "SELECT level FROM admin WHERE id_user = ?";
                    PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin);
                    psAdmin.setInt(1, idUser);

                    ResultSet rsAdmin = psAdmin.executeQuery();
                    if (rsAdmin.next()) {
                        UserSession.setAdminLevel(rsAdmin.getString("level"));
                    }

                    return new Admin(idUser, username, role);
                }

                // ===== PEMINJAM =====
                String prodi = "-";
                String sqlPeminjam = "SELECT prodi FROM peminjam WHERE id_user = ?";
                PreparedStatement psPeminjam = conn.prepareStatement(sqlPeminjam);
                psPeminjam.setInt(1, idUser);

                ResultSet rsPeminjam = psPeminjam.executeQuery();
                if (rsPeminjam.next()) {
                    prodi = rsPeminjam.getString("prodi");
                }

                return new Peminjam(idUser, username, role, prodi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    
    public String[] getNamaEmailById(int idUser) {
        String sql = "SELECT nama, email FROM user WHERE id_user = ?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new String[]{
                        rs.getString("nama"),
                        rs.getString("email")
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
