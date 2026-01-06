package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.GantiPassword;

public class GantiPasswordDAO {
    public boolean updatePassword(GantiPassword data) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE password = ? AND id_user = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, data.getPasswordHashBaru());
            pstmt.setString(2, data.getPasswordHashLama());
            pstmt.setInt(3, data.getIdUser());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}