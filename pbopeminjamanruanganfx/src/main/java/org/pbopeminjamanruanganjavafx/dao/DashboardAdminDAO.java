package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardAdminDAO {

    private final Connection connection;

    public DashboardAdminDAO(Connection connection) {
        this.connection = connection;
    }

    public int countTotalRuangan() {
        String sql = "SELECT COUNT(*) FROM ruangan";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int countTotalPeminjaman() {
        String sql = "SELECT COUNT(*) FROM reservasi";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM reservasi WHERE status = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public List<String[]> getAktivitasTerbaru() {
        List<String[]> list = new ArrayList<>();

        String sql =
            "SELECT r.status, ru.nama_ruangan, r.tanggal_pengajuan " +
            "FROM reservasi r " +
            "JOIN ruangan ru ON r.id_ruangan = ru.id_ruangan " +
            "ORDER BY r.tanggal_pengajuan DESC " +
            "LIMIT 3";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
}
