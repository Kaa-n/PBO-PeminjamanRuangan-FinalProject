package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @FXML
    void btnEdit(ActionEvent event) {
        pindahHalaman("EditAdmin");
    }

    @FXML
    void btnHapus(ActionEvent event) {

    }



 //pindah halaman
    private void pindahHalaman(String namaFxml) {
        try {
            App.setRoot(namaFxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void setData(String nama, String deskripsi, int kapasitas, String status) {
        lblNamaRuangan.setText(nama);
        lblDeskripsi.setText(deskripsi);
        lblKapasitas.setText("Kapasitas: " + kapasitas + " orang");
        lblStatus.setText("Status: " + status);
    }



}
