package org.pbopeminjamanruanganjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
