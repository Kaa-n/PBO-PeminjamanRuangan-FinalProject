package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class DaftarRuanganPeminjamController {

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private FlowPane flowPaneRuangan;

    @FXML
    void btnBeranda(ActionEvent event) {
        pindahHalaman("dashboard_peminjam_new");
    }

    @FXML
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_user");
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
    void btnAkun(ActionEvent event) {
        pindahHalaman("Profile");
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login", "SIPIRANG FMIPA");
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

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
