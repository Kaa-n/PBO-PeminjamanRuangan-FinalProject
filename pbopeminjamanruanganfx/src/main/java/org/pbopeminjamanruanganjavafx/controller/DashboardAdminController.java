package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.dao.DashboardAdminDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardAdminController implements Initializable {

    @FXML private Label lblTotalRuangan;
    @FXML private Label lblTotalPeminjaman;
    @FXML private Label lblDisetujui;
    @FXML private Label lblPending;
    @FXML private Label lblDitolak;


    @FXML private VBox aktivitasContainer;


    @FXML
    private void btnAkun() throws IOException {
        App.setRoot("profile_admin");
    }

    @FXML
    private void btnBeranda() throws IOException {
        App.setRoot("dashboard_admin_new");
    }

    @FXML
    void btnKelolaPeminjaman(ActionEvent event) throws IOException {
        App.setRoot("admin_detail_peminjaman");
    }

    @FXML
    private void btnKelolaRuangan(ActionEvent event) throws IOException {
        App.setRoot("kelola_ruangan");
    }

    @FXML
    private void btnKelolaUser(ActionEvent event) throws IOException {
        App.setRoot("kelola_user");
    }

    @FXML
    private void btnKeluar(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(
                    App.class.getResource("login.fxml"));
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
    void btnKontak(ActionEvent event) throws IOException {
        App.setRoot("kontak_admin");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDashboardData();
        loadAktivitasTerbaru();
    }
    private void loadDashboardData() {
        Connection conn = DatabaseConnection.getConnection();
        DashboardAdminDAO dao = new DashboardAdminDAO(conn);

        lblTotalRuangan.setText(
                String.valueOf(dao.countTotalRuangan())
        );

        lblTotalPeminjaman.setText(
                String.valueOf(dao.countTotalPeminjaman())
        );

        lblDisetujui.setText(
                String.valueOf(dao.countByStatus("disetujui"))
        );

        lblPending.setText(
                String.valueOf(dao.countByStatus("menunggu"))
        );

        lblDitolak.setText(
                String.valueOf(dao.countByStatus("ditolak"))
        );
    }
    private void loadAktivitasTerbaru() {
        Connection conn = DatabaseConnection.getConnection();
        DashboardAdminDAO dao = new DashboardAdminDAO(conn);

        aktivitasContainer.getChildren().clear();

        for (String[] data : dao.getAktivitasTerbaru()) {
            aktivitasContainer.getChildren().add(
                    createAktivitasItem(
                            data[0], // nama ruangan
                            data[1], // status
                            data[2]  // tanggal
                    )
            );
        }
    }

    private VBox createAktivitasItem(
            String namaRuangan,
            String status,
            String tanggal
    ) {
        Label lblInfo = new Label(
                "Ruang " + namaRuangan + " - " + status
        );
        lblInfo.setStyle("-fx-font-size: 16px;");

        Label lblTanggal = new Label(tanggal);
        lblTanggal.setStyle(
                "-fx-text-fill: #777777; -fx-font-size: 12px;"
        );

        Label lblStatus = new Label(status);
        lblStatus.setStyle(getStatusStyle(status));

        VBox box = new VBox(lblInfo, lblTanggal, lblStatus);
        box.setSpacing(6);
        box.setStyle(
                "-fx-background-color: white;" +
                "-fx-padding: 12;" +
                "-fx-background-radius: 10;"
        );

        return box;
    }

    private String getStatusStyle(String status) {
        switch (status.toLowerCase()) {
            case "disetujui":
                return "-fx-background-color: #e6f4ea;" +
                       "-fx-text-fill: #2e7d32;" +
                       "-fx-padding: 4 12;" +
                       "-fx-background-radius: 12;";
            case "menunggu":
                return "-fx-background-color: #fff4e5;" +
                       "-fx-text-fill: #ff8f00;" +
                       "-fx-padding: 4 12;" +
                       "-fx-background-radius: 12;";
            case "ditolak":
                return "-fx-background-color: #fdecea;" +
                       "-fx-text-fill: #c62828;" +
                       "-fx-padding: 4 12;" +
                       "-fx-background-radius: 12;";
            default:
                return "-fx-background-color: #eeeeee;" +
                       "-fx-padding: 4 12;" +
                       "-fx-background-radius: 12;";
        }
    }
}
