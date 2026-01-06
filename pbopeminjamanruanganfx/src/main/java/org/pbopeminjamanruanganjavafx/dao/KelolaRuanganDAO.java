package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection; 
import org.pbopeminjamanruanganjavafx.model.KelolaRuangan;

public class KelolaRuanganDAO {

    public List<KelolaRuangan> getAllRuangan() {
        List<KelolaRuangan> listRuangan = new ArrayList<>();
        String sql = "SELECT * FROM ruangan";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                KelolaRuangan ruangan = new KelolaRuangan(
                    rs.getInt("id_ruangan"),           
                    rs.getString("nama_ruangan"),      
                    rs.getInt("kapasitas"),             
                    rs.getString("status_ruangan"),     
                    rs.getString("tipe_ruangan"),       
                    rs.getString("keterangan_ruangan"), 
                    rs.getString("foto_ruangan")        
                );

                listRuangan.add(ruangan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listRuangan;
    }

    public boolean hapusRuangan(int id) {
        String sql = "DELETE FROM ruangan WHERE id_ruangan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}