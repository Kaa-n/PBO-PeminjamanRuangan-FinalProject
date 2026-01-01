package org.pbopeminjamanruanganjavafx;


import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;

public class RuanganPeminjamController implements Initializable {

    @FXML
    private ScrollPane contentScrollPane;
    @FXML
    private FlowPane flowPaneRuangan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tampilkanDaftarRuangan();

        setupCustomScroll(contentScrollPane);
    }

    // data dummy
    private List<Ruangan> getDataSementara() {
        List<Ruangan> listRuangan = new ArrayList<>();

        // Data 1
        listRuangan.add(new Ruangan(
            "Lab Komputer 1",
            "Laboratorium komputer spesifikasi tinggi untuk praktikum pemrograman dan desain grafis dan dan dan dana  dan dan dan dan dan dand ananana dan anana danana danana dana dana dan dan dan.",
            40,
            "Tersedia",
            "Proyektor, AC, Wifi Kencang, Papan Tulis, Sound System, Meja Komputer Lengkap, Spidol, Kursi Ergonomis",
            "/images/RoomsPage.jpg" 
        ));

        // Data 2
        listRuangan.add(new Ruangan(
            "Aula Serbaguna",
            "Aula besar cocok untuk seminar dan acara himpunan mahasiswa.",
            200,
            "Terpakai",
            "Panggung, Mic Wireless, AC Sentral, Proyektor Besar",
            "/images/RoomsPage.jpg"
        ));

        // Data 3
        listRuangan.add(new Ruangan(
            "Ruang Rapat Dosen",
            "Ruangan privat untuk rapat internal fakultas.",
            10,
            "Maintenance",
            "Meja Bundar, AC, TV LED, Kulkas Mini",
            "/images/RoomsPage.jpg"
        ));
        
        
        listRuangan.add(new Ruangan(
            "Kelas A1", 
            "Kelas reguler.", 
            30, "Tersedia", 
            "AC, Papan Tulis", 
            "/images/RoomsPage.jpg"
        ));

        listRuangan.add(new Ruangan(
            "Kelas A2", 
            "Kelas reguler.", 
            30, 
            "Tersedia", 
            "AC, Papan Tulis", 
            "/images/RoomsPage.jpg"
        ));

        return listRuangan;
    }

    // Method Tampilkan (Looping List)
    private void tampilkanDaftarRuangan() {
        List<Ruangan> dataRuangan = getDataSementara();

        // membersihkan FlowPane
        flowPaneRuangan.getChildren().clear();

        try {
            for (Ruangan ruang : dataRuangan) {
                
                // Load FXML Kartu
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("item_ruangan.fxml"));
                
                // Tipe root sesuai ItemRuangan.fxml
                VBox cardBox = loader.load(); 

                // Panggil Controller Kartu
                ItemRuanganController itemController = loader.getController();

                itemController.setData(
                    ruang.getNama(),
                    ruang.getDeskripsi(),
                    ruang.getKapasitas(),
                    ruang.getStatus(),
                    ruang.getFasilitas(),
                    ruang.getFotoPath()
                );

                // Tambah Kartu ke FlowPane
                flowPaneRuangan.getChildren().add(cardBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void setupCustomScroll(ScrollPane pane) {
        // Faktor kecepatan 
        double kecepatan = 0.005; 

        contentScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0) {
                event.consume(); // mematikan scroll bawaan yang lambat
                
                // geser posisi saat ini dikurangi (arah scroll * kecepatan)
                contentScrollPane.setVvalue(
                    contentScrollPane.getVvalue() - (event.getDeltaY() * kecepatan)
                );
            }
        });
    }
}


