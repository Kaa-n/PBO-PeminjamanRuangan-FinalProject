package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DashboardPeminjamDAO {

    private final Connection connection;

    public DashboardPeminjamDAO(Connection connection) {
        this.connection = connection;
    }

    public int countAllByUser(int idUser) {
        int idPeminjam = getIdPeminjamByUser(idUser);
        if (idPeminjam == -1) return 0;

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

    public int countByStatusByUser(int idUser, String status) {
        int idPeminjam = getIdPeminjamByUser(idUser);
        if (idPeminjam == -1) return 0;

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

    public List<String[]> getAktivitasTerbaruByUser(int idUser) {
        List<String[]> list = new ArrayList<>();
        int idPeminjam = getIdPeminjamByUser(idUser);
        if (idPeminjam == -1) return list;

        String sql =
            "SELECT ru.nama_ruangan, r.status, r.tanggal_pengajuan " +
            "FROM reservasi r " +
            "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
            "WHERE r.id_peminjam = ? " +
            "ORDER BY r.tanggal_pengajuan DESC LIMIT 3";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPeminjam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
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
        private int getIdPeminjamByUser(int idUser) {
        String sql = "SELECT id_peminjam FROM peminjam WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_peminjam");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
}
}