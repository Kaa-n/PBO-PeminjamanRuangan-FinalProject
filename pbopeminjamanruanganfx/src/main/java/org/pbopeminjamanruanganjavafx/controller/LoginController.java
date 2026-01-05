package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.UserDAO;
import org.pbopeminjamanruanganjavafx.model.Admin;
import org.pbopeminjamanruanganjavafx.model.Peminjam;
import org.pbopeminjamanruanganjavafx.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnLupaPassword(ActionEvent event) {


    }

    private UserDAO userDAO = new UserDAO();

    @FXML
    void handleLogin(ActionEvent event) {
        String username_email = txtEmail.getText();
        String password = txtPassword.getText();

        if (username_email.isEmpty() || password.isEmpty()) {
            showAlert("Opsss", "Username/Email dan Password harus diisi.");
            return;
        }

        // Panggil DAO
        User userLogin = userDAO.validasiLogin(username_email, password);
            if (userLogin != null) {
                // Login Sukses
                try {
                bukaDashboard(userLogin.getDashboardFxml(), userLogin.getDashboardTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Yah...", "Username atau Password salah.");
        }
    }


    private void bukaDashboard(String fxmlFile, String title) throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        App.setScene(scene);
        stage.setScene(scene);
        stage.setTitle(title);
        
        // Kembalikan kemampuan resize window untuk dashboard
        stage.setResizable(true); 
        stage.centerOnScreen();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
