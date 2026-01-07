package org.pbopeminjamanruanganjavafx.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Gedung;

public class GedungDAO {
    public List<Gedung> getAllGedung() {
        List<Gedung> list = new ArrayList<>();
        String sql = "SELECT id_gedung, nama_gedung FROM gedung"; 
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Gedung g = new Gedung();
                g.setIdGedung(rs.getInt("id_gedung"));
                g.setNamaGedung(rs.getString("nama_gedung"));
                list.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}