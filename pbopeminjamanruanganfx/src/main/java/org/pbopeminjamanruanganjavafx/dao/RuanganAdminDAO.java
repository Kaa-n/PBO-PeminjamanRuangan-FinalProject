package org.pbopeminjamanruanganjavafx.dao;

import java.sql.*;

import org.pbopeminjamanruanganjavafx.model.Fasilitas;
import org.pbopeminjamanruanganjavafx.model.Ruangan;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;

public class RuanganAdminDAO {

    public boolean insertRuangan(Ruangan r, int idGedung) {
        String sql = "INSERT INTO ruangan (nama_ruangan, id_gedung, kapasitas, lantai, status_ruangan, keterangan_ruangan, foto_ruangan, tipe_ruangan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, r.getNamaRuangan());
            ps.setInt(2, idGedung); 
            ps.setInt(3, r.getKapasitas());
            ps.setInt(4, r.getLantai());
            ps.setString(5, r.getStatus());     
            ps.setString(6, r.getDeskripsi());  
            ps.setString(7, r.getFotoPath());   
            ps.setString(8, r.getTipeRuangan());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                // Ambil ID Ruangan yang baru saja terbentuk
                ResultSet rs = ps.getGeneratedKeys();
                int idRuanganBaru = -1;
                if (rs.next()) {
                    idRuanganBaru = rs.getInt(1);
                }

                // Insert Fasilitas jika ada
                if (idRuanganBaru != -1 && r.getListFasilitas() != null) {
                    insertFasilitasBatch(conn, idRuanganBaru, r.getListFasilitas());
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Gagal Insert Ruangan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateRuangan(Ruangan r, int idGedung) {
        String sqlUpdate = "UPDATE ruangan SET nama_ruangan=?, id_gedung=?, kapasitas=?, lantai=?, status_ruangan=?, keterangan_ruangan=?, foto_ruangan=?, tipe_ruangan=? WHERE id_ruangan=?";
        String sqlDeleteFas = "DELETE FROM fasilitas WHERE id_ruangan=?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update Tabel Ruangan
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setString(1, r.getNamaRuangan());
                ps.setInt(2, idGedung);
                ps.setInt(3, r.getKapasitas());
                ps.setInt(4, r.getLantai());
                ps.setString(5, r.getStatus());
                ps.setString(6, r.getDeskripsi());
                ps.setString(7, r.getFotoPath());
                ps.setString(8, r.getTipeRuangan());
                ps.setInt(9, r.getIdRuangan());
                ps.executeUpdate();
            }

           try (PreparedStatement psDel = conn.prepareStatement(sqlDeleteFas)) {
                psDel.setInt(1, r.getIdRuangan());
                psDel.executeUpdate();
            }

            // Insert fasilitas baru
            if (r.getListFasilitas() != null && !r.getListFasilitas().isEmpty()) {
                insertFasilitasBatch(conn, r.getIdRuangan(), r.getListFasilitas());
            }

            conn.commit(); 
            return true;            
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {} 
            e.printStackTrace();
            return false;
        }   finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) {}
        }
    }

    private void insertFasilitasBatch(Connection conn, int idRuangan, java.util.List<Fasilitas> listFasilitas) {
        String sqlFasilitas = "INSERT INTO fasilitas (id_ruangan, nama_fasilitas) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlFasilitas)) {
            for (Fasilitas f : listFasilitas) {
                ps.setInt(1, idRuangan);
                ps.setString(2, f.getNamaFasilitas()); 
                ps.addBatch(); 
            }
            ps.executeBatch(); 
        } catch (SQLException e) {
            System.err.println("Gagal simpan fasilitas: " + e.getMessage());
        }
    }

    
}