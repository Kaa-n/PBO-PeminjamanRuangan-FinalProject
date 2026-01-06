package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.GantiPasswordDAO;
import org.pbopeminjamanruanganjavafx.model.GantiPassword;
import org.pbopeminjamanruanganjavafx.util.HashSHA;
import org.pbopeminjamanruanganjavafx.util.UserSession;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class GantiPasswordAdminController {

    @FXML
    private TextField txtKonfirmasiSandi;

    @FXML
    private TextField txtSandiBaru;

    @FXML
    private TextField txtSandiSaatIni;

    @FXML
    void btnCloseKataSandi(ActionEvent event) {
        pindahHalaman("profile_admin");
    }

    @FXML
    void btnSimpanSandi(ActionEvent event) {
        String sandiSaatIni = txtSandiSaatIni.getText().trim();
        String sandiBaru = txtSandiBaru.getText();
        String konfirmasiSandi = txtKonfirmasiSandi.getText();

        // 1. Validasi Input (Tetap sama)
        if (sandiSaatIni.isEmpty() || sandiBaru.isEmpty() || konfirmasiSandi.isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Semua field harus diisi!");
            return;
        }
        if (!sandiBaru.equals(konfirmasiSandi)) {
            tampilkanAlert(Alert.AlertType.ERROR, "Gagal", "Konfirmasi password tidak cocok!");
            return;
        }

        boolean suksesUpdate = false;

        // --- BLOK KHUSUS DATABASE ---
        try {
            int idAktif = UserSession.getUser().getIdUser();
            String hashLama = HashSHA.konversiHexString(HashSHA.konversiSHA(sandiSaatIni));
            String hashBaru = HashSHA.konversiHexString(HashSHA.konversiSHA(sandiBaru));

            GantiPassword dataModel = new GantiPassword(idAktif, hashLama, hashBaru);
            GantiPasswordDAO dao = new GantiPasswordDAO();
            
            suksesUpdate = dao.updatePassword(dataModel);
        } catch (Exception e) {
            e.printStackTrace();
            tampilkanAlert(Alert.AlertType.ERROR, "Database Error", "Terjadi kesalahan koneksi database.");
            return; // Berhenti jika DB error
        }

        // --- BLOK NAVIGASI DI LUAR TRY DATABASE ---
        if (suksesUpdate) {
            tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Kata sandi berhasil diperbarui!");
            
            try {
                // Ini yang bikin error "Database Masalah" palsu kalau file FXML Admin belum ada
                String fxmlTujuan = UserSession.getUser().getDashboardFxml();
                App.setRoot(fxmlTujuan);
            } catch (IOException e) {
                e.printStackTrace();
                tampilkanAlert(Alert.AlertType.WARNING, "Navigasi Error", "Sandi berhasil diubah, tapi halaman dashboard '" + UserSession.getUser().getDashboardFxml() + "' belum tersedia.");
            }
        } else {
            tampilkanAlert(Alert.AlertType.ERROR, "Gagal", "Kata sandi saat ini salah!");
        }
    }

    private void tampilkanAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
     //pindah halaman
    private void pindahHalaman(String namaFxml) {
        try {
            App.setRoot(namaFxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}