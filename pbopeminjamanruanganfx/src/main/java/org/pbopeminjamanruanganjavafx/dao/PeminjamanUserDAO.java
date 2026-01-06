package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Peminjaman;

public class PeminjamanUserDAO {

    // Method untuk mengambil history peminjaman milik USER YANG LOGIN saja
    public List<Peminjaman> getPeminjamanSaya(int idUser) {
        List<Peminjaman> list = new ArrayList<>();

        // Query wajib pakai WHERE agar data orang lain tidak muncul
        String query = "SELECT r.id_reservasi, u.nama AS nama_peminjam, ru.nama_ruangan, " +
                "r.tanggal, r.jam_mulai, r.jam_selesai, r.jumlah_peserta, " +
                "r.status, r.keterangan_reservasi " +
                "FROM reservasi r " +
                "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                "JOIN peminjam p ON r.id_peminjam = p.id_peminjam " +
                "JOIN user u ON p.id_user = u.id_user " +
                "WHERE u.id_user = ? " + // <--- KUNCI PEMISAHNYA DI SINI
                "ORDER BY r.tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUser); // Masukkan ID User ke tanda tanya (?)

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Bungkus data ke Model Peminjaman (Modelnya tetap sama ya!)
                list.add(new Peminjaman(
                        rs.getInt("id_reservasi"),
                        rs.getString("nama_peminjam"),
                        rs.getString("nama_ruangan"),
                        rs.getString("tanggal"),
                        rs.getString("jam_mulai") + " - " + rs.getString("jam_selesai"),
                        rs.getInt("jumlah_peserta"),
                        rs.getString("status"),
                        rs.getString("keterangan_reservasi")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal mengambil data history user: " + e.getMessage());
        }
        return list;
    }

        

    // Nanti kalau mau bikin fitur "Batalkan Peminjaman", methodnya taruh di sini
    // juga.
    // public void batalkanPeminjaman(int idReservasi) { ... }
}