package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.config.DatabaseConnection;
import org.pbopeminjamanruanganjavafx.model.Fasilitas;
import org.pbopeminjamanruanganjavafx.model.Ruangan;
import org.pbopeminjamanruanganjavafx.model.Gedung;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ToggleButton;

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


    // Variabel Bantuan
    private List<ToggleButton> slotTerpilih = new ArrayList<>();
    private final int DURASI_SKS = 40; // Menit
    private final int MAX_SLOT = 5;

    // Class menyimpan info booking dari DB
    private class BookingInfo {
        LocalTime start;
        LocalTime end;
        String status;

        public BookingInfo(LocalTime start, LocalTime end, String status) {
            this.start = start;
            this.end = end;
            this.status = status;
        }
    }

    


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

    // Method ini dipanggil oleh ItemRuanganController sebelum scene ditampilkan
    public void setRuangan(Ruangan ruangan) {
        this.ruanganTerpilih = ruangan;
        
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

        flowPaneFasilitas.getChildren().clear();
        if (ruangan.getListFasilitas() != null) {
            for (Fasilitas fas : ruangan.getListFasilitas()) {
                // Format label: "Proyektor (2)" atau hanya "Proyektor" jika jumlah 1
                String labelText = fas.getNamaFasilitas();
                if (fas.getJumlah() > 1) {
                    if (fas.getKondisi().equalsIgnoreCase("baik")) {
                        labelText += " (" + fas.getJumlah() + ")";
                    } else{
                        labelText += " (" + fas.getJumlah() + " - " + fas.getKondisi() + ")";
                    }   
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



        // SETUP DATE PICKER
        txtTanggal.setValue(java.time.LocalDate.now()); // Default Hari Ini
        
        // Matikan tanggal masa lalu
        txtTanggal.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(java.time.LocalDate.now()));
            }
        });

            // Jika tanggal diganti, generate ulang tombol
            txtTanggal.valueProperty().addListener((observable, oldValue, newValue) -> {
                generateTombolWaktu(newValue);
            });

            // Generate pertama kali saat halaman dibuka
            generateTombolWaktu(txtTanggal.getValue());
        }
    
        private void generateTombolWaktu(java.time.LocalDate tanggal) {
        flowPaneRangeWaktu.getChildren().clear();
        slotTerpilih.clear();

        flowPaneRangeWaktu.setHgap(16); 
        flowPaneRangeWaktu.setVgap(16); 

        if (ruanganTerpilih == null || ruanganTerpilih.getGedung() == null) return;
        String sistemJamBuka = ruanganTerpilih.getGedung().getJamBuka();
        //pecah menjadi jam dan menit
        String[] bukaParts = sistemJamBuka.split(":");
        int jamBukaGedung = Integer.parseInt(bukaParts[0]);
        int menitBukaGedung = Integer.parseInt(bukaParts[1]);
        // String sistemJamTutup = ruanganTerpilih.getGedung().getJamTutup();
        String sistemJamTutup = ruanganTerpilih.getGedung().getJamTutup();
        //pecah menjadi jam dan menit
        String[] tutupParts = sistemJamTutup.split(":");
        int jamTutupGedung = Integer.parseInt(tutupParts[0]);
        int menitTutupGedung = Integer.parseInt(tutupParts[1]);
        LocalTime jamBuka = LocalTime.of(jamBukaGedung, menitBukaGedung);
        LocalTime jamTutup = LocalTime.of(jamTutupGedung, menitTutupGedung);
        List<BookingInfo> listBooking = ambilJadwalDariDB(ruanganTerpilih.getIdRuangan(), tanggal);

        LocalTime currentSlot = jamBuka;
        int totalSlotHariIni = 0;
        int slotTerisi = 0;

        while (currentSlot.plusMinutes(DURASI_SKS).isBefore(jamTutup) || currentSlot.plusMinutes(DURASI_SKS).equals(jamTutup)) {
            LocalTime endSlot = currentSlot.plusMinutes(DURASI_SKS);
            String textJam = currentSlot.toString() + " - " + endSlot.toString();
            
            ToggleButton btn = new ToggleButton(textJam);
            
            btn.setPrefSize(114, 38);   // Lebar 114, Tinggi 38
            btn.setMinSize(114, 38);    
            btn.setMaxSize(114, 38);    
            
            btn.getStyleClass().add("button-slot");
            
            // Simpan data jam di tombol
            btn.setUserData(new BookingInfo(currentSlot, endSlot, "tersedia"));

            // CEK STATUS DATABASE-
            boolean isBooked = false;
            for (BookingInfo info : listBooking) {
                if (currentSlot.isBefore(info.end) && endSlot.isAfter(info.start)) {
                    if (info.status.equals("disetujui")) {
                        btn.getStyleClass().add("button-booked");
                        btn.setDisable(true);
                    } else if (info.status.equals("menunggu")) {
                        btn.getStyleClass().add("button-waiting");
                        btn.setDisable(true);
                    }
                    isBooked = true;
                    slotTerisi++;
                    break;
                }
            }

            if (!isBooked) {
                btn.setOnAction(e -> handleKlikSlot(btn));
            }

            flowPaneRangeWaktu.getChildren().add(btn);
            totalSlotHariIni++;
            currentSlot = endSlot;
        }

        updateStatusLabelHarian(totalSlotHariIni, slotTerisi);

        javafx.scene.control.Button btnKhusus = new javafx.scene.control.Button("Khusus");
        btnKhusus.getStyleClass().add("button-khusus");
        btnKhusus.setPrefHeight(38);
        btnKhusus.setPrefSize(114, 38);   // Lebar 114, Tinggi 38
        btnKhusus.setMinSize(114, 38);    // Kunci agar tidak mengecil
        btnKhusus.setMaxSize(114, 38);    // Kunci agar tidak membesar 
        
        btnKhusus.setOnAction(e -> {
            navigasiKeForm(null, null, true);
        });
        
        flowPaneRangeWaktu.getChildren().add(btnKhusus);

    }

    private void updateStatusLabelHarian(int totalSlot, int slotTerisi) {
        String statusFisik = ruanganTerpilih.getStatus(); 
        
        if (!"tersedia".equalsIgnoreCase(statusFisik)) {
            lblStatus.setText(statusFisik); 
            lblStatus.setTextFill(Color.RED);
            return; 
        }

        if (totalSlot > 0 && totalSlot == slotTerisi) {
            lblStatus.setText("Penuh");
            lblStatus.setTextFill(Color.RED);
        } else {
            lblStatus.setText("Tersedia");
            lblStatus.setTextFill(Color.GREEN);
        }
    }

   
    // LOGIKA SELEKSI (MAX 5 & HARUS BERURUTAN)
    private void handleKlikSlot(ToggleButton btn) {
        if (btn.isSelected()) {
            // Cek 1: Max 5
            if (slotTerpilih.size() >= MAX_SLOT) {
                btn.setSelected(false);
                System.out.println("Maksimal 5 slot!");
                return;
            }

            if (!slotTerpilih.isEmpty()) {
                BookingInfo infoBaru = (BookingInfo) btn.getUserData();
                
                // Ambil slot pertama dan terakhir yang sedang dipilih
                BookingInfo first = (BookingInfo) slotTerpilih.get(0).getUserData();
                BookingInfo last = (BookingInfo) slotTerpilih.get(slotTerpilih.size() - 1).getUserData();

                // Cek apakah nempel di depan atau di belakang
                boolean isNext = infoBaru.start.equals(last.end); // Nyambung di akhir
                boolean isPrev = infoBaru.end.equals(first.start); // Nyambung di awal

                if (!isNext && !isPrev) {
                    btn.setSelected(false);
                    System.out.println("Harus memilih jam secara berurutan!");
                    return;
                }
                
                // Jika nyambung di awal, masukkan ke index 0 agar list tetap urut
                if (isPrev) {
                    slotTerpilih.add(0, btn);
                } else {
                    slotTerpilih.add(btn);
                }
            } else {
                // Jika list kosong, langsung masuk
                slotTerpilih.add(btn);
            }

        } else {
            // Hanya boleh uncheck ujung kiri atau ujung kanan agar tidak bolong tengah
            if (slotTerpilih.size() > 1) {
                ToggleButton firstBtn = slotTerpilih.get(0);
                ToggleButton lastBtn = slotTerpilih.get(slotTerpilih.size() - 1);

                if (btn != firstBtn && btn != lastBtn) {
                    // User coba uncheck bagian tengah
                    btn.setSelected(true); // Paksa nyala lagi
                } else {
                    slotTerpilih.remove(btn);
                }
            } else {
                slotTerpilih.remove(btn);
            }
        }      
        System.out.println("Slot terpilih: " + slotTerpilih.size());
    }

    // Method untuk ambil jadwal dari DB
    private List<BookingInfo> ambilJadwalDariDB(int idRuangan, java.time.LocalDate tanggal) {
        List<BookingInfo> list = new ArrayList<>();

        String query = "SELECT jam_mulai, jam_selesai, status FROM reservasi " +
                       "WHERE id_ruangan = ? AND tanggal = ? AND status IN ('menunggu', 'disetujui')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idRuangan);
            stmt.setDate(2, java.sql.Date.valueOf(tanggal));
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Time tStart = rs.getTime("jam_mulai");
                Time tEnd = rs.getTime("jam_selesai");
                String status = rs.getString("status");
                
                list.add(new BookingInfo(tStart.toLocalTime(), tEnd.toLocalTime(), status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    private void navigasiKeForm(LocalTime start, LocalTime end, boolean isKhusus) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("FromPeminjaman.fxml")); 
            
            Parent root = loader.load();
            FromPeminjamanController controller = loader.getController();

            controller.setDataPeminjaman(
                this.ruanganTerpilih, 
                txtTanggal.getValue(), 
                start, 
                end, 
                isKhusus
            );

            App.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }









    @FXML
    void btnAjukanPeminjaman(ActionEvent event) {
        
        if (slotTerpilih.isEmpty()) {
            System.out.println("Harap pilih jam terlebih dahulu!");
            return;
        }

        BookingInfo firstSlot = (BookingInfo) slotTerpilih.get(0).getUserData();
        BookingInfo lastSlot = (BookingInfo) slotTerpilih.get(slotTerpilih.size() - 1).getUserData();

        // Panggil navigasi mode Standard (Khusus = false)
        navigasiKeForm(firstSlot.start, lastSlot.end, false);
        
        
        // pindahHalaman("FromPeminjaman");
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

