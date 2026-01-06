package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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