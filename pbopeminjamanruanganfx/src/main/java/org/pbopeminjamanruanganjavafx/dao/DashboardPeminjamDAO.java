package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.model.Peminjaman;

public class DashboardPeminjamDAO {

    private final Connection connection;

    public DashboardPeminjamDAO(Connection connection) {
        this.connection = connection;
    }

    public int countAll(int idPeminjam) {
        String sql = "SELECT COUNT(*) FROM reservasi WHERE id_peminjam = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPeminjam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countByStatus(int idPeminjam, String status) {
        String sql = "SELECT COUNT(*) FROM reservasi WHERE id_peminjam = ? AND status = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPeminjam);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String[]> getAktivitasTerbaru(int idPeminjam) {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT r.status, ru.nama_ruangan, r.tanggal_pengajuan "
                + "FROM reservasi r "
                + "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan "
                + "WHERE r.id_peminjam = ? "
                + "ORDER BY r.tanggal_pengajuan DESC LIMIT 3";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPeminjam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[] {
                        rs.getString("nama_ruangan"),
                        rs.getString("status"),
                        rs.getTimestamp("tanggal_pengajuan").toString()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- BAGIAN INI YANG DIPERBAIKI ---
    public List<Peminjaman> getPeminjamanByUserId(int idUser) {
        List<Peminjaman> list = new ArrayList<>();

        // 1. Tambahkan r.no_telepon di query
        String query = "SELECT r.id_reservasi, u.nama AS nama_peminjam, ru.nama_ruangan, " +
                "r.tanggal, r.jam_mulai, r.jam_selesai, r.jumlah_peserta, " +
                "r.status, r.keterangan_reservasi, r.no_telepon " + // <--- Update disini
                "FROM reservasi r " +
                "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
                "JOIN peminjam p ON r.id_peminjam = p.id_peminjam " +
                "JOIN user u ON p.id_user = u.id_user " +
                "WHERE u.id_user = ? " +
                "ORDER BY r.tanggal DESC";

        // Gunakan 'connection' milik class ini, bukan buka baru lagi
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // 2. Masukkan data ke Constructor Model yang baru (9 parameter)
                list.add(new Peminjaman(
                        rs.getInt("id_reservasi"),
                        rs.getString("nama_peminjam"),
                        rs.getString("nama_ruangan"),
                        rs.getString("tanggal"),
                        rs.getString("jam_mulai") + " - " + rs.getString("jam_selesai"),
                        rs.getInt("jumlah_peserta"),
                        rs.getString("status"),
                        rs.getString("keterangan_reservasi"), // Note
                        rs.getString("no_telepon")            // Kontak
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}