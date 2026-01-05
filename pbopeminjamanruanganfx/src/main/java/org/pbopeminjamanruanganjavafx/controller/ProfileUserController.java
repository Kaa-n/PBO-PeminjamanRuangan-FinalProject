package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ProfileUserController {

    @FXML
    private ImageView imgTampilanProfile;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNIM;

    @FXML
    private TextField txtNama;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnCloseEditProfile(ActionEvent event) {
        pindahHalaman("dashboard_admin_new");
    }

    @FXML
    void btnGantiKataSandi(ActionEvent event) {
        pindahHalaman("ganti_password");
    }

    @FXML
    void btnPilihGambar(ActionEvent event) {
        // Implementation for choosing an image
    }

    @FXML
    void btnSimpan(ActionEvent event) {
    
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
