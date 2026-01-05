package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

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
        pindahHalaman("dashboard_admin_new");
    }

    @FXML
    void btnKelolaRuangan(ActionEvent event) {
        pindahHalaman("kelola_ruangan");
    }

    @FXML
    void btnKelolaPeminjaman(ActionEvent event) {
        pindahHalaman("admin_detail_peminjaman");
    }

    @FXML
    void btnKelolaUser(ActionEvent event) {
        pindahHalaman("kelola_user");
    }

    @FXML
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_admin");
    }

    @FXML
    void btnAkun(ActionEvent event) {
        pindahHalaman("profile_admin");
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN");
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

}
