package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardPeminjamController {
    @FXML
    private void btnAkun() throws IOException {
        try {
            App.setRoot("Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnBeranda() throws IOException {
        try {
            App.setRoot("dashboard_peminjam_new");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnRuangan(ActionEvent event) {
        try {
            App.setRoot("ruangan_peminjam");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnStatus(ActionEvent event) {
        try {
            App.setRoot("user_detail_peminjaman");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnKeluar(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("LOGIN SIPIRANG");

            // Kembalikan kemampuan resize window untuk dashboard
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKontak(ActionEvent event) {
        try {
            App.setRoot("kontak_user");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
