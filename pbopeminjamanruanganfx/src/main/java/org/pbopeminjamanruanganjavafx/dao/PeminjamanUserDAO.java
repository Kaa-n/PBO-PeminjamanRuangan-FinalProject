package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Peminjaman;

public class PeminjamanUserDAO {

    public List<Peminjaman> getPeminjamanSaya(int idUser) {
        List<Peminjaman> list = new ArrayList<>();

        String query = "SELECT r.id_reservasi, u.nama AS nama_peminjam, ru.nama_ruangan, " +
                "r.tanggal, r.jam_mulai, r.jam_selesai, r.jumlah_peserta, " +
                "r.status, r.keterangan_reservasi, r.no_telepon " + 
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
                        rs.getString("no_telepon") // Kontak
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


    public boolean simpanReservasi(int idPeminjam, int idRuangan, int idTujuan, 
                                   int jumlahPeserta, LocalDate tanggal, 
                                   LocalTime jamMulai, LocalTime jamSelesai, 
                                   String noTelepon, String keterangan) {
        
        String query = "INSERT INTO reservasi " +
                       "(id_peminjam, id_ruangan, id_tujuan, jumlah_peserta, tanggal, " +
                       "jam_mulai, jam_selesai, no_telepon, keterangan_reservasi, status) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'menunggu')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idPeminjam);
            stmt.setInt(2, idRuangan);
            stmt.setInt(3, idTujuan);
            stmt.setInt(4, jumlahPeserta);
            stmt.setDate(5, java.sql.Date.valueOf(tanggal));
            stmt.setTime(6, java.sql.Time.valueOf(jamMulai));
            stmt.setTime(7, java.sql.Time.valueOf(jamSelesai));
            stmt.setString(8, noTelepon);
            stmt.setString(9, keterangan);

            int result = stmt.executeUpdate();
            return result > 0; // Mengembalikan true jika berhasil simpan

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public class DataPemohon {
        public String nama;
        public String nim;
        public String noTelepon; 
    }


    public DataPemohon getDetailPemohon(int idUser) {
        DataPemohon data = null;
        
        // Query Join antara tabel User (ambil nama) dan Peminjam (ambil NIM)
        String query = "SELECT u.nama, p.nomor_induk " +
                       "FROM user u " +
                       "JOIN peminjam p ON u.id_user = p.id_user " +
                       "WHERE u.id_user = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                data = new DataPemohon();
                data.nama = rs.getString("nama");
                data.nim = rs.getString("nomor_induk");
                // data.noTelepon = rs.getString("no_telepon"); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


}