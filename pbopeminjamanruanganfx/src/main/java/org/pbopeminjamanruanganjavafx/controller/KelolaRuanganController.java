package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.KelolaRuanganDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KelolaRuanganController implements Initializable {

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private FlowPane flowPaneRuangan;

    @FXML
    private Label txtTotalRuangan;

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
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN SIPIRANG");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_admin");
    }

    @FXML
    void btnTambahRuangan(ActionEvent event) {
        pindahHalaman("TambahRuangAdmin");
    }

    //pindah halaman
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
        
        // Kembalikan kemampuan resize window untuk dashboard
        stage.setResizable(false); 
        stage.centerOnScreen();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemRuangan();
    }

    public void loadItemRuangan() {
        flowPaneRuangan.getChildren().clear();

        KelolaRuanganDAO dao = new KelolaRuanganDAO();
        var listRuangan = dao.getAllRuangan();

        txtTotalRuangan.setText(String.valueOf(listRuangan.size()));

        for (var ruangan : listRuangan) {
            try {
                FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/pbopeminjamanruanganjavafx/item_kelola_ruangan.fxml")
                );

                VBox card = loader.load();

                ItemKelolaRuanganController controller = loader.getController();
                controller.setData(ruangan, this);

                flowPaneRuangan.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}