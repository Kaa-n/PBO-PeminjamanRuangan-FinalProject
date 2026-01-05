package org.pbopeminjamanruanganjavafx.controller;
import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TambahRuanganAdminController {

    @FXML
    private TextField txtFasilitas;

    @FXML
    private TextField txtKapasitas;

    @FXML
    private TextField txtLokasi;

    @FXML
    private TextField txtNamaRuangan;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnBatal(ActionEvent event) {
        pindahHalaman("kelola_ruangan");
    }

    @FXML
    void btnPilihGambar(ActionEvent event) {

    }

    @FXML
    void btnTambahRuangan(ActionEvent event) {
        pindahHalaman("kelola_ruangan");

    }
private void pindahHalaman(String namaFxml) {
        try {
            App.setRoot(namaFxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
