package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.dao.DashboardPeminjamDAO;
import org.pbopeminjamanruanganjavafx.model.User;
import org.pbopeminjamanruanganjavafx.util.UserSession;

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
        App.setRoot("Profile");
    }

    @FXML
    private void btnBeranda() throws IOException {
        App.setRoot("dashboard_peminjam_new");
    }

    @FXML
    private void btnRuangan(ActionEvent event) throws IOException {
        App.setRoot("ruangan_peminjam");
    }

    @FXML
    private void btnStatus(ActionEvent event) throws IOException {
        App.setRoot("user_detail_peminjaman");
    }

    @FXML
    private void btnKontak(ActionEvent event) throws IOException {
        App.setRoot("kontak_user");
    }

    @FXML
    private void btnKeluar(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();

            FXMLLoader loader =
                    new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("LOGIN SIPIRANG");
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private Label lblTotalPeminjaman;
    @FXML private Label lblDisetujui;
    @FXML private Label lblPending;
    @FXML private Label lblDitolak;

    @FXML private VBox aktivitasContainer;

    @FXML
    public void initialize() {
        User userLogin = UserSession.getUser();

        if (userLogin == null) {
            System.out.println("ERROR: User belum login");
            return;
        }

        loadDashboard(userLogin.getIdUser());
    }

    private void loadDashboard(int idPeminjam) {
        Connection conn = DatabaseConnection.getConnection();
        DashboardPeminjamDAO dao = new DashboardPeminjamDAO(conn);

        lblTotalPeminjaman.setText(
                String.valueOf(dao.countAll(idPeminjam))
        );
        lblDisetujui.setText(
                String.valueOf(dao.countByStatus(idPeminjam, "disetujui"))
        );
        lblPending.setText(
                String.valueOf(dao.countByStatus(idPeminjam, "menunggu"))
        );
        lblDitolak.setText(
                String.valueOf(dao.countByStatus(idPeminjam, "ditolak"))
        );

        List<String[]> aktivitas =
                dao.getAktivitasTerbaru(idPeminjam);

        aktivitasContainer.getChildren().clear();

        for (String[] data : aktivitas) {
            aktivitasContainer.getChildren().add(
                    createAktivitasItem(
                            data[0],
                            data[1], 
                            data[2]  
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
