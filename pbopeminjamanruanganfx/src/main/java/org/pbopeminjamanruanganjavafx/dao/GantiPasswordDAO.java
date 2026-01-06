package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.GantiPassword;

public class GantiPasswordDAO {

    public boolean updatePassword(GantiPassword data) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE id_user = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, data.getPasswordHashBaru());
            pstmt.setInt(2, data.getIdUser());
            pstmt.setString(3, data.getPasswordHashLama());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}