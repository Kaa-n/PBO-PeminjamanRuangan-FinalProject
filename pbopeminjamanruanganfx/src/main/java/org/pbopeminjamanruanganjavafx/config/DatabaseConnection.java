package org.pbopeminjamanruanganjavafx.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Konfigurasi Database
    private static final String URL = "jdbc:mysql://localhost/db_peminjaman_ruangan_pbo";
    private static final String USER = "root"; 
    private static final String PASSWORD = "turzi"; 

    // Variabel statis untuk menyimpan koneksi
    private static Connection connection;

    // Method untuk mendapatkan koneksi ke database.
    public static Connection getConnection() {
        try {
            // Cek jika koneksi belum ada atau sudah tertutup agar tidak membuat koneksi
            // baru setiap kali dipanggil
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    System.err.println("Driver MySQL tidak ditemukan. Pastikan dependency sudah ditambahkan.");
                    e.printStackTrace();
                    return null;
                }

                // Membuka koneksi
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke database.");
            e.printStackTrace();
        }
        return connection;
    }
}

// cara penggunaan:

// import java.sql.Connection;
// import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;

// public void namaKelas() {
// Connection conn = DatabaseConnection.getConnection();

// if (conn != null) {
// // query SELECT, INSERT, dll di sini
// }
// }
