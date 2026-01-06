package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Fasilitas;
import org.pbopeminjamanruanganjavafx.model.Gedung;
import org.pbopeminjamanruanganjavafx.model.Ruangan;

public class RuanganDAO {
    public List<Ruangan> getAllRuangan() {
        List<Ruangan> listRuangan = new ArrayList<>();
        
        // QUERY JOIN: Mengambil data Ruangan & data Gedung 
        String query = "SELECT r.*, g.nama_gedung, g.jam_buka, g.jam_tutup " +
                       "FROM ruangan r " +
                       "JOIN gedung g ON r.id_gedung = g.id_gedung";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Ambil data Ruangan
                Ruangan ruangan = new Ruangan(
                    rs.getInt("id_ruangan"),
                    rs.getString("nama_ruangan"),
                    rs.getString("keterangan_ruangan"),
                    rs.getInt("kapasitas"),
                    rs.getInt("lantai"),
                    rs.getString("status_ruangan"),
                    rs.getString("foto_ruangan"),
                    rs.getString("tipe_ruangan")
                );

                // Ambil data Gedung dari hasil JOIN
                Gedung gedung = new Gedung(
                    rs.getInt("id_gedung"),
                    rs.getString("nama_gedung"),
                    rs.getString("jam_buka").toString(), // Konversi Time ke String
                    rs.getString("jam_tutup").toString()
                );
                
                // Masukkan data Gedung ke dalam Ruangan
                ruangan.setGedung(gedung);

                // Ambil List Fasilitas (Query terpisah berdasarkan ID Ruangan)
                List<Fasilitas> fasilitas = getFasilitasByRuanganId(ruangan.getIdRuangan(), conn);
                ruangan.setListFasilitas(fasilitas);

                listRuangan.add(ruangan);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRuangan;
    }

    // Helper method untuk mengambil fasilitas
    private List<Fasilitas> getFasilitasByRuanganId(int idRuangan, Connection conn) {
        List<Fasilitas> list = new ArrayList<>();
        String query = "SELECT * FROM fasilitas WHERE id_ruangan = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, idRuangan);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    list.add(new Fasilitas(
                        rs.getInt("id_fasilitas"),
                        rs.getString("nama_fasilitas"),
                        rs.getInt("jumlah"),
                        rs.getString("kondisi")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
