package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.model.Fasilitas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.pbopeminjamanruanganjavafx.model.Ruangan;


public class DetailRuanganController {

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private FlowPane flowPaneFasilitas;

    @FXML
    private FlowPane flowPaneRangeWaktu;

    @FXML
    private FlowPane flowPaneRuangan;

    @FXML
    private StackPane imageContainer;

    @FXML
    private ImageView imgFotoRuangan;

    @FXML
    private Label lblDeskripsi;

    @FXML
    private Label lblKapasitas;

    @FXML
    private Label lblLokasi;

    @FXML
    private Label lblNamaRuangan;

    @FXML
    private Label lblPesan;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTipeRuang;

    @FXML
    private DatePicker txtTanggal;

    


    private Ruangan ruanganTerpilih; // Variabel untuk menyimpan data yang diterima

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

    // Method ini akan dipanggil oleh ItemRuanganController sebelum scene ditampilkan
    public void setRuangan(Ruangan ruangan) {
        this.ruanganTerpilih = ruangan;
        
        // Update UI Detail sesuai data ruangan
        lblNamaRuangan.setText(ruangan.getNamaRuangan());
        lblTipeRuang.setText(ruangan.getTipeRuangan());
        lblDeskripsi.setText(ruangan.getDeskripsi());
        lblKapasitas.setText(String.valueOf(ruangan.getKapasitas()) + " Orang");
        
        if (ruangan.getGedung() != null) {
            lblLokasi.setText(ruangan.getGedung().getNamaGedung() + ", Lantai " + ruangan.getLantai());
        }

        if ("Tersedia".equalsIgnoreCase(ruangan.getStatus())) {
            lblStatus.setTextFill(Color.GREEN);
        } else {
            lblStatus.setTextFill(Color.RED);
        }

        // // Tampilkan fasilitas (gabungkan jadi string koma)
        // StringBuilder fasStr = new StringBuilder();
        // if(ruangan.getListFasilitas() != null) {
        //     for(Fasilitas f : ruangan.getListFasilitas()){
        //         fasStr.append(f.getNamaFasilitas()).append(", ");
        //     }
        // }
        // flowPaneFasilitas.getChildren().clear();
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

        // Load Gambar
        try {
            String path = ruangan.getFotoPath();
            if (path == null || path.isEmpty()) path = "/images/RoomsPage.png";
            imgFotoRuangan.setImage(new Image(getClass().getResourceAsStream(path)));
        } catch (Exception e) { }
    }
















    @FXML
    void btnAjukanPeminjaman(ActionEvent event) {
        pindahHalaman("FromPeminjaman");
    }

    @FXML
    void btnAkun(ActionEvent event) {
        pindahHalaman("Profile");
    }

    @FXML
    void btnBeranda(ActionEvent event) {
        pindahHalaman("dashboard_peminjam_new");
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN SIPIRANG");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKembali(ActionEvent event) {
        pindahHalaman("ruangan_peminjam");
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
    void btnKontak(ActionEvent event) {
        pindahHalaman("kontak_user");
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

