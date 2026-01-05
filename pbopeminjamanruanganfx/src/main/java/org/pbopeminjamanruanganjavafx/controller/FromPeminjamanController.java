package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

}
