package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.io.File;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.KelolaRuanganDAO;
import org.pbopeminjamanruanganjavafx.model.Fasilitas;
import org.pbopeminjamanruanganjavafx.model.KelolaRuangan;
import org.pbopeminjamanruanganjavafx.model.Ruangan;
import org.pbopeminjamanruanganjavafx.controller.EditAdminController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class ItemKelolaRuanganController {
    

    @FXML
    private FlowPane flowPaneFasilitas;

    @FXML
    private StackPane imageContainer;

    @FXML
    private ImageView imgFotoRuangan;

    @FXML
    private Label lblDeskripsi;

    @FXML
    private Label lblKapasitas;

    @FXML
    private Label lblNamaRuangan;

    @FXML
    private Label lblStatus;

    private Ruangan currentRuangan;
    private int idRuangan;
    private KelolaRuanganController mainController;

    public void setData(Ruangan ruangan, KelolaRuanganController mainController) {
        this.idRuangan = ruangan.getIdRuangan();
        this.currentRuangan = ruangan; //simpan data ke variabel
        this.mainController = mainController;

        lblNamaRuangan.setText(ruangan.getNamaRuangan());
        lblDeskripsi.setText(ruangan.getDeskripsi());
        lblKapasitas.setText("Kapasitas: " + ruangan.getKapasitas() + " orang");
        lblStatus.setText(ruangan.getStatus()); 
        //update warna status
        
        String status = ruangan.getStatus().toLowerCase();
        if (status.equals("tersedia")) {
            lblStatus.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;"); 
        } else if (status.equals("penuh")) {
            lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;"); 
        } else if (status.equals("dalam perbaikan")) {
            lblStatus.setStyle("-fx-text-fill: #f1c40f; -fx-font-weight: bold;"); 
        }
        // Menampilkan gambar ruangan
        if (ruangan.getFotoPath() != null && !ruangan.getFotoPath().isEmpty()) {
            try {
                java.io.File file = new java.io.File(ruangan.getFotoPath());
                if (file.exists()) {
                    javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
                    imgFotoRuangan.setImage(image);
                }
            } catch (Exception e) {
                System.out.println("Gagal memuat gambar: " + e.getMessage());
            }
        }
        flowPaneFasilitas.getChildren().clear();
        if (ruangan.getListFasilitas() != null) {
            for (Fasilitas fas : ruangan.getListFasilitas()) {
                
                String labelText = fas.getNamaFasilitas();
                if (fas.getJumlah() > 1) {
                    labelText += " (" + fas.getJumlah() + ")";
                }

                Label lbl = buatLabelFasilitas(labelText);
                flowPaneFasilitas.getChildren().add(lbl);
            }
        }
    }

    private Label buatLabelFasilitas(String teks) {
        Label lbl = new Label(teks);
        lbl.setPrefHeight(22);
        lbl.setAlignment(Pos.CENTER);
        lbl.setStyle("-fx-background-color: #F3F3F3; " +
                     "-fx-background-radius: 8; " +
                     "-fx-padding: 0 8 0 8; " +
                     "-fx-text-fill: #676767; " +
                     "-fx-font-size: 11px;");
        return lbl;
    }

    @FXML
    void btnEdit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("EditAdmin.fxml"));
            Parent root = loader.load();

            //Ambil Controller dari halaman Edit yang baru dimuat
            EditAdminController controller = loader.getController();

            if (this.currentRuangan != null) {
                controller.setRuanganData(this.currentRuangan); 
            } else {
                System.out.println("Error: Data ruangan null!");
            }

            //tampilkan halaman edit yang sesuai
            Scene scene = new Scene(root);
            App.setScene(scene); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnHapus(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText(null);
        alert.setContentText("Hapus ruangan ini?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            KelolaRuanganDAO dao = new KelolaRuanganDAO();
            if (dao.hapusRuangan(idRuangan)) {
                mainController.loadItemRuangan();
            } else {
                System.out.println("Gagal menghapus data dari database.");
            }
        }
    }

    private void pindahHalaman(String namaFxml) {
        try {
            App.setRoot(namaFxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}