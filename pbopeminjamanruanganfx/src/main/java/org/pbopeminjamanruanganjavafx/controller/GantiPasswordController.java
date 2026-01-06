package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.GantiPasswordDAO;
import org.pbopeminjamanruanganjavafx.model.GantiPassword;
import org.pbopeminjamanruanganjavafx.util.HashSHA;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class GantiPasswordController {
    // Variabel untuk menampung ID user yang sedang login
    private int idUserLogin;

    @FXML
    private TextField txtKonfirmasiSandi;

    @FXML
    private TextField txtSandiBaru;

    @FXML
    private TextField txtSandiSaatIni;

    // Method ini akan dipanggil oleh LoginController saat pindah halaman
    public void setIdUserLogin(int idUser) {
        this.idUserLogin = idUser;
    }
    
    @FXML
    void btnCloseKataSandi(ActionEvent event) {
        pindahHalaman("Profile");
    }

    @FXML
    void btnSimpanSandi(ActionEvent event) {
        String sandiSaatIni = txtSandiSaatIni.getText().trim();
        String sandiBaru = txtSandiBaru.getText();
        String konfirmasiSandi = txtKonfirmasiSandi.getText();

        // 1. Validasi Input Kosong
        if (sandiSaatIni.isEmpty() || sandiBaru.isEmpty() || konfirmasiSandi.isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Semua field harus diisi!");
            return;
        }

        // 2. Validasi Konfirmasi Password Baru
        if (!sandiBaru.equals(konfirmasiSandi)) {
            tampilkanAlert(Alert.AlertType.ERROR, "Gagal", "Konfirmasi password tidak cocok!");
            return;
        }

        try {
            // 3. Proses Hashing (Sesuaikan nama method .hash() jika berbeda di HashSHA.java)
            String hashLama = HashSHA.hash(sandiSaatIni);
            String hashBaru = HashSHA.hash(sandiBaru);

            // 4. Bungkus ke Model (ID user sementara 2)
            GantiPassword dataModel = new GantiPassword(2, hashLama, hashBaru);

            // 5. Panggil DAO untuk eksekusi ke database
            GantiPasswordDAO dao = new GantiPasswordDAO();
            boolean sukses = dao.updatePassword(dataModel);

            if (sukses) {
                tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Kata sandi berhasil diperbarui!");
                pindahHalaman("Profile");
            } else {
                tampilkanAlert(Alert.AlertType.ERROR, "Gagal", "Kata sandi saat ini salah!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            tampilkanAlert(Alert.AlertType.ERROR, "Database Error", "Terjadi kesalahan koneksi database.");
        }
    }

    // Helper untuk menampilkan notifikasi ke user
    private void tampilkanAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void pindahHalaman(String namaFxml) {
        try {
            App.setRoot(namaFxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}