package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Peminjaman;

public class PeminjamanUserDAO {

    public List<Peminjaman> getPeminjamanSaya(int idUser) {
        List<Peminjaman> list = new ArrayList<>();

        String query = "SELECT r.id_reservasi, u.nama AS nama_peminjam, ru.nama_ruangan, " +
                "r.tanggal, r.jam_mulai, r.jam_selesai, r.jumlah_peserta, " +
                "r.status, r.keterangan_reservasi, r.no_telepon " + // <--- TAMBAH INI
                "FROM reservasi r " +
                "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                "JOIN peminjam p ON r.id_peminjam = p.id_peminjam " +
                "JOIN user u ON p.id_user = u.id_user " +
                "WHERE u.id_user = ? " +
                "ORDER BY r.tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUser);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Peminjaman(
                        rs.getInt("id_reservasi"),
                        rs.getString("nama_peminjam"),
                        rs.getString("nama_ruangan"),
                        rs.getString("tanggal"),
                        rs.getString("jam_mulai") + " - " + rs.getString("jam_selesai"),
                        rs.getInt("jumlah_peserta"),
                        rs.getString("status"),
                        rs.getString("keterangan_reservasi"), // Note
                        rs.getString("no_telepon") // Kontak (ambil dari kolom no_telepon)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal mengambil data history user: " + e.getMessage());
        }
        return list;
    }

    public boolean batalkanPeminjaman(int idReservasi) {
        String query = "DELETE FROM reservasi WHERE id_reservasi = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idReservasi);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}