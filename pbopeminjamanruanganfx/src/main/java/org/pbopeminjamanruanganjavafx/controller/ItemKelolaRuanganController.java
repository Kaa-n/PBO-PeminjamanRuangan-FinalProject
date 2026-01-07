package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.KelolaRuanganDAO;
import org.pbopeminjamanruanganjavafx.model.KelolaRuangan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class ItemKelolaRuanganController {
    private int idRuangan;
    private KelolaRuanganController mainController;

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

    public void setData(KelolaRuangan ruangan, KelolaRuanganController mainController) {
        this.idRuangan = ruangan.getIdRuangan();
        this.mainController = mainController;

        lblNamaRuangan.setText(ruangan.getNamaRuangan());
        lblDeskripsi.setText(ruangan.getKeteranganRuangan());
        lblKapasitas.setText("Kapasitas: " + ruangan.getKapasitas() + " orang");
        lblStatus.setText(ruangan.getStatusRuangan()); 
        //update warna status
        
        String status = ruangan.getStatusRuangan().toLowerCase();
        if (status.equals("tersedia")) {
            lblStatus.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;"); 
        } else if (status.equals("penuh")) {
            lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;"); 
        } else if (status.equals("dalam perbaikan")) {
            lblStatus.setStyle("-fx-text-fill: #f1c40f; -fx-font-weight: bold;"); 
        }
        // Menampilkan gambar ruangan
        if (ruangan.getFotoRuangan() != null && !ruangan.getFotoRuangan().isEmpty()) {
            try {
                java.io.File file = new java.io.File(ruangan.getFotoRuangan());
                if (file.exists()) {
                    javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
                    imgFotoRuangan.setImage(image);
                }
            } catch (Exception e) {
                System.out.println("Gagal memuat gambar: " + e.getMessage());
            }
        }
    }

    @FXML
    void btnEdit(ActionEvent event) {
        pindahHalaman("EditAdmin");
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