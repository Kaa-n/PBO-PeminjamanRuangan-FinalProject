package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.pbopeminjamanruanganjavafx.model.Ruangan;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FromPeminjamanController {

    @FXML
    private Label txtInformasiPemesanan;

    @FXML
    private TextField txtJam;

    @FXML
    private TextField txtJumlahPeserta;

    @FXML
    private Label txtKembaliKeJadwal;

    @FXML
    private TextField txtNamaLengkap;

    @FXML
    private TextField txtNomorKontak;

    @FXML
    private TextField txtRuangan;

    @FXML
    private TextField txtTanggal;

    @FXML
    private Label txtTanggalHariPeminjam;

    @FXML
    private TextField txtTujuanPeminjaman;

    @FXML
    void btnBeranda(ActionEvent event) {
        pindahHalaman("dashboard_peminjam_new");
    }

    @FXML
    void btnRuangan(ActionEvent event) {
        pindahHalaman("ruangan_peminjam");
    }

    @FXML
    void btnStatus(ActionEvent event) {
        pindahHalaman("user_detail_peminjaman");
    }

    @FXML
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_user");
    }

    @FXML
    void btnAkun(ActionEvent event) {
        pindahHalaman("Profile");
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN SIPIRANG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnBatal(ActionEvent event) {
        pindahHalaman("detail_ruangan_dan_jadwal_peminjam");
    }

    @FXML
    void btnKirimPengajuan(ActionEvent event) {
        pindahHalaman("ruangan_peminjam");

    }

    @FXML
    void btnKembali(ActionEvent event) {
        pindahHalaman("detail_ruangan_dan_jadwal_peminjam");
    }

    @FXML
    void pindahHalaman(String namaFxml) {
        try {
            App.setRoot(namaFxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pindahLogin(ActionEvent event, String fxmlFile, String title) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);

        // Kembalikan kemampuan resize window untuk dashboard
        stage.setResizable(false);
        stage.centerOnScreen();

    }





    private Ruangan ruanganTerpilih;
    private LocalDate tanggalTerpilih;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private boolean isKhusus;

    // --- METHOD INI YANG AKAN DIPANGGIL DARI DETAIL RUANGAN ---
    public void setDataPeminjaman(Ruangan ruangan, LocalDate tanggal, LocalTime mulai, LocalTime selesai, boolean khusus) {
        this.ruanganTerpilih = ruangan;
        this.tanggalTerpilih = tanggal;
        this.jamMulai = mulai;
        this.jamSelesai = selesai;
        this.isKhusus = khusus;

        // 1. Isi & Kunci Ruangan
        txtRuangan.setText(ruangan.getNamaRuangan());
        txtRuangan.setDisable(true); // Read-only

        // 2. Isi & Kunci Tanggal (Format: 12-01-2026)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtTanggal.setText(tanggal.format(fmt));
        txtTanggal.setDisable(true); // Read-only

        // 3. Logika Jam (Standard vs Khusus)
        if (isKhusus) {
            // Mode Khusus: Kosongkan dan biarkan user mengetik
            txtJam.setText("");
            txtJam.setPromptText("Contoh: 07.00 - 15.00");
            txtJam.setDisable(false); // BISA DIEDIT
            txtJam.setEditable(true);
        } else {
            // Mode Normal: Isi otomatis dan kunci
            String range = mulai + " - " + selesai;
            txtJam.setText(range);
            txtJam.setDisable(true); // TIDAK BISA DIEDIT
        }
        
        // (Opsional) Auto-fill kapasitas max
        txtJumlahPeserta.setPromptText("Max: " + ruangan.getKapasitas());
    }

    @FXML
    private void handleSimpan() {
        // Logika simpan ke database...
        // Jika isKhusus == true, ambil string jam mentah dari txtJam.getText()
        // Jika isKhusus == false, pakai variabel jamMulai & jamSelesai
    }

}
