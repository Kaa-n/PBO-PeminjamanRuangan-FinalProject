
package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.model.Fasilitas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import org.pbopeminjamanruanganjavafx.model.Ruangan;

public class ItemRuanganController {

    @FXML
    private Button btnPesan;

    @FXML
    private FlowPane flowPaneFasilitas;

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

    @FXML private StackPane imageContainer;
    
    private Ruangan ruanganSaatIni;

    public void setData(Ruangan ruangan) {
        this.ruanganSaatIni = ruangan;
        lblNamaRuangan.setText(ruangan.getNamaRuangan()); //ubah nama ruangan pada card baru
        lblDeskripsi.setText(ruangan.getDeskripsi());
        lblKapasitas.setText("Kapasitas: " + ruangan.getKapasitas() + " orang");
        lblStatus.setText("Status: " + ruangan.getStatus());

        // WARNA STATUS
        
         String status = ruangan.getStatus().toLowerCase();
        if (status.equals("tersedia")) {
            lblStatus.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;"); 
        } else if (status.equals("penuh")) {
            lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;"); 
        } else if (status.equals("dalam perbaikan")) {
            lblStatus.setStyle("-fx-text-fill: #f1c40f; -fx-font-weight: bold;"); 
        }

        try {
            String path = ruangan.getFotoPath();
            if (path == null || path.isEmpty()) {
                path = "/images/RoomsPage.png"; 
            }
            
            Image img = new Image(getClass().getResourceAsStream(path), 341, 213, false, true);
            imgFotoRuangan.setImage(img);
            
            clipImage(); 

        } catch (Exception e) {
            System.out.println("Gagal load gambar: " + ruangan.getFotoPath());
        }

        // LOAD FASILITAS
        flowPaneFasilitas.getChildren().clear();
        if (ruangan.getListFasilitas() != null) {
            for (Fasilitas fas : ruangan.getListFasilitas()) {
                // Format label: "Proyektor (2)" atau hanya "Proyektor" jika jumlah 1
                String labelText = fas.getNamaFasilitas();
                if (fas.getJumlah() > 1) {
                    labelText += " (" + fas.getJumlah() + ")";
                }

                Label lbl = buatLabelFasilitas(labelText);
                flowPaneFasilitas.getChildren().add(lbl);
            }
        }
        
    }

    private void clipImage() {
        imageContainer.setStyle("-fx-background-radius: 16 16 0 0;");
        Rectangle clipShape = new Rectangle();
        clipShape.widthProperty().bind(imageContainer.widthProperty());
        clipShape.heightProperty().bind(imageContainer.heightProperty());
        clipShape.setArcWidth(16);
        clipShape.setArcHeight(16);
        imageContainer.setClip(clipShape);
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
    private void btnDetailPengajuan(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pbopeminjamanruanganjavafx/detail_ruangan_dan_jadwal_peminjam.fxml"));

            //test
            if (this.ruanganSaatIni == null) {
                System.out.println("Ruangan saat ini masih null!");
            }
            Parent root = loader.load();

            DetailRuanganController detailController = loader.getController();


            if (detailController != null) {
                detailController.setRuangan(this.ruanganSaatIni);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            
            
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal membuka detail ruangan.");
        }
    }

}