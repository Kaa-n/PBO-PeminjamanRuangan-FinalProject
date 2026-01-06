package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.dao.DashboardPeminjamDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    @FXML
    private Label lblTotalPeminjaman;

    @FXML
    private Label lblDisetujui;

    @FXML
    private Label lblPending;

    @FXML
    private Label lblDitolak;

    @FXML
    private VBox aktivitasContainer;

    // sementara (dummy)
    private final int ID_PEMINJAM = 1;

    @FXML
    public void initialize() {
        loadDashboard();
    }

    private void loadDashboard() {
        Connection conn = DatabaseConnection.getConnection();
        DashboardPeminjamDAO dao = new DashboardPeminjamDAO(conn);

        // ===== CARD DATA =====
        lblTotalPeminjaman.setText(
            String.valueOf(dao.countAll(ID_PEMINJAM))
        );
        lblDisetujui.setText(
            String.valueOf(dao.countByStatus(ID_PEMINJAM, "disetujui"))
        );
        lblPending.setText(
            String.valueOf(dao.countByStatus(ID_PEMINJAM, "menunggu"))
        );
        lblDitolak.setText(
            String.valueOf(dao.countByStatus(ID_PEMINJAM, "ditolak"))
        );

        // ===== AKTIVITAS TERBARU =====
        List<String[]> aktivitas = dao.getAktivitasTerbaru(ID_PEMINJAM);
        aktivitasContainer.getChildren().clear();

        for (String[] a : aktivitas) {
            Label lbl = new Label(
                a[0] + " • " + a[1] + " • " + a[2]
            );
            lbl.setStyle("-fx-padding: 8;");
            aktivitasContainer.getChildren().add(lbl);
        }
    }
}
