package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditAdminController {

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
    void btnSimpanPerubahan(ActionEvent event) {
        pindahHalaman("kelola_ruangan");
    }


@FXML
    void btnAkun(ActionEvent event) {
        pindahHalaman("admin_profile");
    }

    @FXML
    void btnBeranda(ActionEvent event) {
        pindahHalaman("dashboard_admin_new");
    }

    @FXML
    void btnKelolaPeminjaman(ActionEvent event) {
        pindahHalaman("admin_detail_peminjaman");
    }

    @FXML
    void btnKelolaRuangan(ActionEvent event) {
        pindahHalaman("kelola_ruangan");
    }

    @FXML
    void btnKelolaUser(ActionEvent event) {
        pindahHalaman("kelola_user");
    }


    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN SIPIRANG");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_admin");
    }

    @FXML
    void btnTambahRuangan(ActionEvent event) {
        pindahHalaman("TambahRuangAdmin");
    }

    //pindah halaman
    private void pindahHalaman(String namaFxml) {
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
