package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.dao.KontakDAO;
import org.pbopeminjamanruanganjavafx.model.Kontak;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KontakAdminController implements Initializable {

    @FXML
    void btnAkun() throws IOException {
        try {
            App.setRoot("profile_admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnBeranda() throws IOException {
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
    void btnKelolaRuangan(ActionEvent event) {
        try {
            App.setRoot("kelola_ruangan");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKelolaUser(ActionEvent event) {
        try {
            App.setRoot("kelola_user");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKeluar(ActionEvent event) {
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
    @FXML private VBox pesanContainer;
    @FXML private Label lblNama;
    @FXML private Label lblEmail;
    @FXML private Label lblSubjek;
    @FXML private Label lblTanggal;
    @FXML private Label lblIsiPesan;
    @FXML private Pagination paginationPesan;

    private KontakDAO kontakDAO;
    private static final int DATA_PER_PAGE = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = DatabaseConnection.getConnection();
        kontakDAO = new KontakDAO(conn);

        int totalData = kontakDAO.countKontak();
        int totalPage = (int) Math.ceil((double) totalData / DATA_PER_PAGE);

        paginationPesan.setPageCount(Math.max(totalPage, 1));
        paginationPesan.setCurrentPageIndex(0);

        loadPage(0);

        paginationPesan.currentPageIndexProperty().addListener(
            (obs, oldIndex, newIndex) -> loadPage(newIndex.intValue())
        );
    }

    private void loadPage(int pageIndex) {
        int offset = pageIndex * DATA_PER_PAGE;

        List<Kontak> list = kontakDAO.getKontakPerPage(DATA_PER_PAGE, offset);

        if (list.isEmpty()) {
            clearUI();
            return;
        }

        Kontak k = list.get(0);

        lblNama.setText(k.getNama());
        lblEmail.setText(k.getEmail());
        lblSubjek.setText(k.getSubjek());
        lblIsiPesan.setText(k.getPesan());
        lblTanggal.setText(
            k.getCreatedAt().format(
                DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")
            )
        );

        pesanContainer.setUserData(k.getIdKontak());
    }
    @FXML
    void btnHapusPesan() {

        Object data = pesanContainer.getUserData();
        if (data == null) return;

        int idKontak = (int) data;

        boolean success = kontakDAO.deleteById(idKontak);

        if (success) {
            showAlert("Sukses", "Pesan berhasil dihapus.");

            int totalData = kontakDAO.countKontak();
            int totalPage = (int) Math.ceil((double) totalData / DATA_PER_PAGE);
            paginationPesan.setPageCount(Math.max(totalPage, 1));

            int currentPage = paginationPesan.getCurrentPageIndex();
            if (currentPage >= totalPage && currentPage > 0) {
                paginationPesan.setCurrentPageIndex(currentPage - 1);
            } else {
                loadPage(currentPage);
            }
        } else {
            showAlert("Gagal", "Pesan gagal dihapus.");
        }
    }

    // ===== UTIL =====
    private void clearUI() {
        lblNama.setText("-");
        lblEmail.setText("-");
        lblSubjek.setText("-");
        lblIsiPesan.setText("-");
        lblTanggal.setText("-");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
