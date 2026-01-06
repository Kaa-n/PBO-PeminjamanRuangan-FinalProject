package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;

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

        private void loadItemRuangan() {
        flowPaneRuangan.getChildren().clear();

        for (int i = 1; i <= 4; i++) {
            try {
                // 1. Ambil lokasi file FXML
                URL fxmlLocation = getClass().getResource("/org/pbopeminjamanruanganjavafx/item_kelola_ruangan.fxml");
                
                if (fxmlLocation == null) {
                    System.err.println("Error: File item_kelola_ruangan.fxml tidak ditemukan!");
                    continue; 
                }

                // 2. Inisialisasi loader dengan lokasi yang benar
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                
                // 3. Load FXML (Cukup satu kali panggil load())
                // Pastikan Root di FXML adalah VBox sesuai dengan file Anda
                VBox card = loader.load(); 

                // 4. Ambil controller dan isi data
                // Pastikan di Scene Builder 'Controller Class' sudah diatur ke ItemKelolaRuanganController
                ItemKelolaRuanganController controller = loader.getController();

                if (controller != null) {
                    controller.setData(
                        "Lab Komputer " + i,
                        "Laboratorium komputer dengan spesifikasi tinggi untuk praktikum",
                        40,
                        "Tersedia"
                    );
                }

                // 5. Masukkan ke FlowPane
                flowPaneRuangan.getChildren().add(card);

            } catch (IOException e) {
                System.err.println("Gagal memuat item ke-" + i);
                e.printStackTrace();
            }
        }
    }

}