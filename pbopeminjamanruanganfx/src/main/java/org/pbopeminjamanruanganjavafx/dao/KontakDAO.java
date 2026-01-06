package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.model.Kontak;

public class KontakDAO {

    private final Connection connection;

    public KontakDAO(Connection connection) {
        this.connection = connection;
    }
    public void insert(Kontak kontak) throws SQLException {

        String sql = "INSERT INTO kontak (id_user, nama, email, subjek, pesan) "
                   + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        if (kontak.getIdUser() != null) {
            ps.setInt(1, kontak.getIdUser());
        } else {
            ps.setNull(1, java.sql.Types.INTEGER);
        }

        ps.setString(2, kontak.getNama());
        ps.setString(3, kontak.getEmail());
        ps.setString(4, kontak.getSubjek());
        ps.setString(5, kontak.getPesan());

        ps.executeUpdate();
    }

    public List<Kontak> getKontakPerPage(int limit, int offset) {
        List<Kontak> list = new ArrayList<>();

        String sql = "SELECT id_kontak, nama, email, subjek, pesan, created_at "
                   + "FROM kontak "
                   + "ORDER BY created_at DESC "
                   + "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Kontak k = new Kontak();
                k.setIdKontak(rs.getInt("id_kontak"));
                k.setNama(rs.getString("nama"));
                k.setEmail(rs.getString("email"));
                k.setSubjek(rs.getString("subjek"));
                k.setPesan(rs.getString("pesan"));
                k.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
                );

                list.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countKontak() {
        String sql = "SELECT COUNT(*) FROM kontak";

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
    public boolean deleteById(int idKontak) {
        String sql = "DELETE FROM kontak WHERE id_kontak = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idKontak);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
