package org.pbopeminjamanruanganjavafx.dao;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Peminjaman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PeminjamanAdminDAO {

    // 1. Method Ambil SEMUA Data (Tanpa filter ID User)
    public List<Peminjaman> getAllPeminjaman() {
        List<Peminjaman> list = new ArrayList<>();

        // Query mengambil semua data user + detail ruangan
        String query = "SELECT r.id_reservasi, u.nama AS nama_peminjam, ru.nama_ruangan, " +
                "r.tanggal, r.jam_mulai, r.jam_selesai, r.jumlah_peserta, " +
                "r.status, r.keterangan_reservasi, r.no_telepon " +
                "FROM reservasi r " +
                "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                "JOIN peminjam p ON r.id_peminjam = p.id_peminjam " +
                "JOIN user u ON p.id_user = u.id_user " +
                "ORDER BY r.tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Peminjaman(
                        rs.getInt("id_reservasi"),
                        rs.getString("nama_peminjam"),
                        rs.getString("nama_ruangan"),
                        rs.getString("tanggal"),
                        rs.getString("jam_mulai") + " - " + rs.getString("jam_selesai"),
                        rs.getInt("jumlah_peserta"),
                        rs.getString("status"),
                        rs.getString("keterangan_reservasi"),
                        rs.getString("no_telepon")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Method Update Status (Terima / Tolak)
    public boolean updateStatusReservasi(int idReservasi, String statusBaru) {
        String query = "UPDATE reservasi SET status = ? WHERE id_reservasi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, statusBaru);
            ps.setInt(2, idReservasi);

            int rows = ps.executeUpdate();
            return rows > 0; // Return true jika berhasil

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}