package org.pbopeminjamanruanganjavafx.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.ProfileAdminDAO;
import org.pbopeminjamanruanganjavafx.model.ProfileAdmin;
import org.pbopeminjamanruanganjavafx.model.User;
import org.pbopeminjamanruanganjavafx.util.UserSession;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ProfileAdminController implements Initializable {

    @FXML private ImageView imgTampilanProfile;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNIM; 
    @FXML private TextField txtNama;
    @FXML private TextField txtUsername;

    private ProfileAdminDAO profileDAO = new ProfileAdminDAO();
    private File selectedImageFile;

   @Override
    public void initialize(URL url, ResourceBundle rb) {
        User userLoggedIn = UserSession.getUser();
        
        if (userLoggedIn != null) {
            // Ambil data LENGKAP dari DB karena class User session tidak punya field nama & email
            ProfileAdmin dataLengkap = profileDAO.getProfileById(userLoggedIn.getIdUser());
            
            if (dataLengkap != null) {
                txtNIM.setText(String.valueOf(dataLengkap.getIdUser()));
                txtNama.setText(dataLengkap.getNama());
                txtUsername.setText(dataLengkap.getUsername());
                txtEmail.setText(dataLengkap.getEmail());
            }

        }
    }

    @FXML
    void btnSimpan(ActionEvent event) {
        try {
            int idUser = Integer.parseInt(txtNIM.getText()); 
            String nama = txtNama.getText();
            String username = txtUsername.getText();
            String email = txtEmail.getText();

            ProfileAdmin updatedProfile = new ProfileAdmin(idUser, nama, username, email);

            if (profileDAO.updateProfile(updatedProfile)) {
                // Tampilkan alert sukses (Hapus bagian update session yang merah)
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data profil berhasil diperbarui!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal memperbarui database.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Terjadi kesalahan: " + e.getMessage());
        }
    }

    @FXML
    void btnCloseEditProfile(ActionEvent event) {
        pindahHalaman("dashboard_admin_new");
    }

    @FXML
    void btnGantiKataSandi(ActionEvent event) {
        pindahHalaman("ganti_password_admin");
    }

    @FXML
    void btnPilihGambar(ActionEvent event) {
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void pindahHalaman(String namaFxml) {
        try {
            App.setRoot(namaFxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}