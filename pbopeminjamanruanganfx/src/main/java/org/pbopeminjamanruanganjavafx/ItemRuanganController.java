package org.pbopeminjamanruanganjavafx;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    


    public void setData(String nama, String deskripsi, int kapasitas, String status, String fasilitasStr, String imagePath) {
        
        // Set Teks Dasar
        lblNamaRuangan.setText(nama);
        lblDeskripsi.setText(deskripsi);
        lblKapasitas.setText("Kapasitas: " + kapasitas + " orang");
        lblStatus.setText("Status: " + status);

        // Warna Status
        if (status.equalsIgnoreCase("Tersedia")) {
            lblStatus.setTextFill(Color.GREEN);
        } else {
            lblStatus.setTextFill(Color.RED);
        }

        // Load Gambar
        try {
            
            Image placeholder = new Image(getClass().getResourceAsStream(imagePath), 341, 213, false, true);
            imgFotoRuangan.setImage(placeholder);
            imageContainer.setStyle("-fx-background-radius: 16 16 0 0;");
            // bentuk kliping yang sama persis untuk wadahnya.
            Rectangle clipShape = new Rectangle();
            clipShape.widthProperty().bind(imageContainer.widthProperty());
            clipShape.heightProperty().bind(imageContainer.heightProperty());
            
            clipShape.setArcWidth(16);
            clipShape.setArcHeight(16);
            imageContainer.setClip(clipShape);
        } catch (Exception e) {
            
            System.out.println("Gagal load gambar: " + imagePath);
        }

        // Generate Label untuk Fasilitas
        flowPaneFasilitas.getChildren().clear(); // Bersihkan dummy data
        if (fasilitasStr != null && !fasilitasStr.isEmpty()) {
            String[] daftarFasilitas = fasilitasStr.split(","); // Pecah berdasarkan koma

            for (String fas : daftarFasilitas) { // Loop dan buat Label untuk tiap fasilitas
                Label labelTag = buatLabelFasilitas(fas.trim());
                flowPaneFasilitas.getChildren().add(labelTag); // Tambah ke FlowPane
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

}
