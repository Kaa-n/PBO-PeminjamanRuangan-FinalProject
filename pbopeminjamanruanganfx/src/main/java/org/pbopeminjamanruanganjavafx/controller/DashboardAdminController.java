package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardAdminController {

    @FXML private Label lblTotalRuangan;
    @FXML private Label lblTotalPeminjaman;
    @FXML private Label lblDisetujui;
    @FXML private Label lblPending;
    @FXML private Label lblDitolak;

    @FXML private VBox aktivitasContainer;
  

    @FXML
    private void btnAkun() throws IOException {
        try {
            App.setRoot("profile_admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnBeranda() throws IOException {
        try {
            App.setRoot("dashboard_admin_new");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKelolaPeminjaman(ActionEvent event) {
        try {
            App.setRoot("admin_detail_peminjaman");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnKelolaRuangan(ActionEvent event) {
        try {
            App.setRoot("kelola_ruangan");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnKelolaUser(ActionEvent event) {
        try {
            App.setRoot("kelola_user");
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

            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKontak(ActionEvent event) {
        try {
            App.setRoot("kontak_admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Dummy sementara agar tidak null & tidak error
        if (lblTotalRuangan != null) lblTotalRuangan.setText("0");
        if (lblTotalPeminjaman != null) lblTotalPeminjaman.setText("0");
        if (lblDisetujui != null) lblDisetujui.setText("0");
        if (lblPending != null) lblPending.setText("0");
        if (lblDitolak != null) lblDitolak.setText("0");
    }
}