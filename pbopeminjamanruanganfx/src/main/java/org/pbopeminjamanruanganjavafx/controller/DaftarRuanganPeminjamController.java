package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.RuanganDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pbopeminjamanruanganjavafx.controller.ItemRuanganController;
import org.pbopeminjamanruanganjavafx.model.Ruangan;

public class DaftarRuanganPeminjamController implements Initializable {

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private FlowPane flowPaneRuangan;

    private RuanganDAO ruanganDAO = new RuanganDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataRuangan();

        setupCustomScroll(contentScrollPane);
    }

    private void loadDataRuangan() {
        // Ambil List Ruangan (sudah berisi Gedung & Fasilitas) dari Database
        List<Ruangan> dataRuangan = ruanganDAO.getAllRuangan();

        // Bersihkan tampilan flowpane sebelum menambahkan item baru
        flowPaneRuangan.getChildren().clear();

        try {
            for (Ruangan r : dataRuangan) {
                // Load FXML Item
                FXMLLoader loader = new FXMLLoader();
                URL fxmlLocation = getClass().getResource("/org/pbopeminjamanruanganjavafx/item_ruangan.fxml");

                loader.setLocation(fxmlLocation); // Set lokasi FXML
                VBox cardBox = loader.load(); // root dari item_ruangan.fxml

                // Get Controller Item
                ItemRuanganController itemController = loader.getController();
                
                // Set Data ke Item
                itemController.setData(r);

                // Tambahkan ke FlowPane utama
                flowPaneRuangan.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupCustomScroll(ScrollPane pane) {
        double kecepatan = 0.005; 
        contentScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0) {
                event.consume(); 
                contentScrollPane.setVvalue(
                    contentScrollPane.getVvalue() - (event.getDeltaY() * kecepatan)
                );
            }
        });
    }

    @FXML
    void btnBeranda(ActionEvent event) {
        pindahHalaman("dashboard_peminjam_new");
    }

    @FXML
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_user");
    }

    @FXML
    void btnRuangan(ActionEvent event) {
       pindahHalaman("ruangan_peminjam");
    }


    @FXML
    void btnStatus(ActionEvent event) {
        pindahHalaman("user_detail_peminjaman");
    }


    @FXML
    void btnAkun(ActionEvent event) {
        pindahHalaman("Profile");
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN SIPIRANG");
        } catch (IOException e) {
            e.printStackTrace();
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
        
        // Kembalikan kemampuan resize window untuk dashboard
        stage.setResizable(false); 
        stage.centerOnScreen();
    }

}
