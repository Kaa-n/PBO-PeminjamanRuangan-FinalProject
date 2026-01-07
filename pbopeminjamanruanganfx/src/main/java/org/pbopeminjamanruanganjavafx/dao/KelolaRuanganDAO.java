package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Fasilitas;
import org.pbopeminjamanruanganjavafx.model.KelolaRuangan;
import org.pbopeminjamanruanganjavafx.model.Ruangan;

public class KelolaRuanganDAO {

    public List<Ruangan> getAllRuangan() {
        List<Ruangan> list = new ArrayList<>();
        String sql = "SELECT * FROM ruangan";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ruangan r = new Ruangan();

                int idRuangan = rs.getInt("id_ruangan");
                r.setIdRuangan(idRuangan);
                r.setIdGedung(rs.getInt("id_gedung"));
                r.setNamaRuangan(rs.getString("nama_ruangan"));
                r.setKapasitas(rs.getInt("kapasitas"));
                r.setDeskripsi(rs.getString("keterangan_ruangan"));
                r.setStatus(rs.getString("status_ruangan"));
                r.setFotoPath(rs.getString("foto_ruangan"));
                r.setLantai(rs.getInt("lantai"));
                r.setTipeRuangan(rs.getString("tipe_ruangan"));
                
                List<Fasilitas> listFas = getFasilitasByRuanganId(idRuangan, conn);
                r.setListFasilitas(listFas);

                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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

    private List<Fasilitas> getFasilitasByRuanganId(int idRuangan, Connection conn) {
        List<Fasilitas> fasilitas = new ArrayList<>();
        String sql = "SELECT * FROM fasilitas WHERE id_ruangan = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idRuangan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Fasilitas f = new Fasilitas();
                    f.setIdFasilitas(rs.getInt("id_fasilitas"));
                    f.setNamaFasilitas(rs.getString("nama_fasilitas"));
                    fasilitas.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fasilitas;
    }
}