package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.sql.Connection;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.dao.KontakDAO;
import org.pbopeminjamanruanganjavafx.model.Kontak;
import org.pbopeminjamanruanganjavafx.util.UserSession;
import org.pbopeminjamanruanganjavafx.dao.UserDAO;
import org.pbopeminjamanruanganjavafx.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class KontakPeminjamController {

    @FXML private TextField txtNama;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSubjek;
    @FXML private TextArea txtPesan;

    @FXML
    private void btnAkun() throws IOException {
        App.setRoot("Profile");
    }

    @FXML
    private void btnBeranda() throws IOException {
        App.setRoot("dashboard_peminjam_new");
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
    private void btnStatus(ActionEvent event) {
        try {
            App.setRoot("user_detail_peminjaman");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnKontak(ActionEvent event) {
        try {
            App.setRoot("kontak_user");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnKeluar(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnKirimPesan(ActionEvent event) {
        String nama = txtNama.getText();
        String email = txtEmail.getText();
        String subjek = txtSubjek.getText();
        String pesan = txtPesan.getText();

        if (nama.isEmpty() || email.isEmpty() || subjek.isEmpty() || pesan.isEmpty()) {
            showAlert("Peringatan", "Semua field wajib diisi.");
            return;
        }

        // Cek apakah user sudah login
        User user = UserSession.getUser();
        if (user == null) {
            showAlert("Error", "Anda harus login untuk mengirim pesan.");
            return;
        }

        try {
            Kontak kontak = new Kontak();
            kontak.setIdUser(user.getIdUser());
            kontak.setNama(nama);
            kontak.setEmail(email);
            kontak.setSubjek(subjek);
            kontak.setPesan(pesan);

            Connection conn = DatabaseConnection.getConnection();
            KontakDAO kontakDAO = new KontakDAO(conn);
            kontakDAO.insert(kontak);

            showAlert("Sukses", "Pesan berhasil dikirim.");
            txtSubjek.clear();
            txtPesan.clear();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Gagal mengirim pesan: " + e.getMessage());
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void initialize() {
        User user = UserSession.getUser();

            if (user != null) {
                UserDAO userDAO = new UserDAO();
                String[] data = userDAO.getNamaEmailById(user.getIdUser());

            if (data != null) {
                txtNama.setText(data[0]);
                txtEmail.setText(data[1]);

                txtNama.setEditable(false);
                txtEmail.setEditable(false);
            }
        }
    }
}
