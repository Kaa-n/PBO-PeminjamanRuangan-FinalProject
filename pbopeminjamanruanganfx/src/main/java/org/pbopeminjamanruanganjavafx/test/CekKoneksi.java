package org.pbopeminjamanruanganjavafx.test;

import java.sql.Connection;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;

public class CekKoneksi {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("terhubung ke MySQL.");
        } else {
            System.out.println("gagal terhubung ke MySQL.");
        }
    }
}
