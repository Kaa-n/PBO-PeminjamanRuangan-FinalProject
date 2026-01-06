package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.KelolaUserDAO;
import org.pbopeminjamanruanganjavafx.util.UserSession;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class KelolaUserController {

    /* =========================
       FXML FIELD (WAJIB DI ATAS)
       ========================= */

    @FXML private VBox boxNomorInduk;
    @FXML private VBox boxProdi;

    @FXML private ComboBox<String> comboBoxPeran;

    @FXML private TextField txtNama;
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNomorInduk;
    @FXML private TextField txtProdi;

    @FXML private Label txtTotalUser;

    @FXML private ScrollPane contentScrollPane;
    @FXML private FlowPane flowPaneRuangan1;

    /* =========================
       INITIALIZE
       ========================= */

    @FXML
    public void initialize() {

        // sembunyikan field peminjam di awal
        boxNomorInduk.setManaged(false);
        boxNomorInduk.setVisible(false);

        boxProdi.setManaged(false);
        boxProdi.setVisible(false);

        initComboBoxPeran();

        comboBoxPeran.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                handlePeranChange(newVal);
            }
        });
    }

    /* =========================
       LOGIKA ROLE
       ========================= */

    private void initComboBoxPeran() {
    comboBoxPeran.getItems().clear();

    if (UserSession.getUser() == null) {
        System.out.println("WARNING: UserSession masih null");
        return;
    }

    String roleLogin = UserSession.getUser().getRole();

    // === PEMINJAM TIDAK BOLEH BUAT USER ===
    if ("peminjam".equalsIgnoreCase(roleLogin)) {
        return;
    }

    // === ADMIN ===
    if ("admin".equalsIgnoreCase(roleLogin)) {

        String adminLevel = UserSession.getAdminLevel();

        if ("super_admin".equalsIgnoreCase(adminLevel)) {
            comboBoxPeran.getItems().addAll(
                "super_admin",
                "staf",
                "user"
            );
        } else {
            // staf
            comboBoxPeran.getItems().add("user");
        }
    }

    comboBoxPeran.setPromptText("Pilih Peran");
    comboBoxPeran.getSelectionModel().clearSelection();
}

    private void handlePeranChange(String role) {
        boolean isUser = "user".equalsIgnoreCase(role);

        boxNomorInduk.setManaged(isUser);
        boxNomorInduk.setVisible(isUser);

        boxProdi.setManaged(isUser);
        boxProdi.setVisible(isUser);
    }

    /* =========================
       BUTTON NAVIGATION
       ========================= */

    @FXML
    void btnAkun(ActionEvent event) {
        pindahHalaman("profile_admin");
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
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_admin");
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN SIPIRANG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDaftarAkun(ActionEvent event) {

    String nama = txtNama.getText();
    String username = txtUsername.getText();
    String email = txtEmail.getText();
    String peran = comboBoxPeran.getValue();

    if (nama.isEmpty() || username.isEmpty() || email.isEmpty() || peran == null) {
        showAlert("Peringatan", "Semua field wajib diisi");
        return;
    }

    String nim = txtNomorInduk.getText();
    String prodi = txtProdi.getText();

    if ("user".equalsIgnoreCase(peran) && (nim.isEmpty() || prodi.isEmpty())) {
        showAlert("Peringatan", "NIM dan Prodi wajib diisi untuk User");
        return;
    }

    try {
        KelolaUserDAO dao = new KelolaUserDAO();

        if (dao.isUsernameOrEmailExist(username, email)) {
            showAlert("Gagal", "Username atau Email sudah digunakan");
            return;
        }

        // role tabel user
        String roleUser = "user".equalsIgnoreCase(peran) ? "peminjam" : "admin";

        int idUser = dao.insertUser(nama, username, email, roleUser);

        if ("user".equalsIgnoreCase(peran)) {
            dao.insertPeminjam(idUser, nim, prodi);
        } else {
            dao.insertAdmin(idUser, peran); // super_admin / staf
        }

        showAlert("Sukses", "Akun berhasil dibuat\nPassword default: 12345678");
        resetForm();

    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Error", "Gagal membuat akun");
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
        stage.setResizable(false);
        stage.centerOnScreen();
    }
    private void resetForm() {
        txtNama.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtNomorInduk.clear();
        txtProdi.clear();
        comboBoxPeran.getSelectionModel().clearSelection();
        boxNomorInduk.setVisible(false);
        boxNomorInduk.setManaged(false);
        boxProdi.setVisible(false);
        boxProdi.setManaged(false);
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}