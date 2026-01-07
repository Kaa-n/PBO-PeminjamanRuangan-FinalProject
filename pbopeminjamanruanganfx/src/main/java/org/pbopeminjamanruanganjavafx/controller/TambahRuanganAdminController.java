package org.pbopeminjamanruanganjavafx.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.pbopeminjamanruanganjavafx.App;

import java.io.IOException;
import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.model.Ruangan;
import org.pbopeminjamanruanganjavafx.dao.GedungDAO;
import org.pbopeminjamanruanganjavafx.dao.RuanganDAO;
import org.pbopeminjamanruanganjavafx.dao.RuanganAdminDAO;
import org.pbopeminjamanruanganjavafx.model.Fasilitas;
import org.pbopeminjamanruanganjavafx.model.Gedung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class TambahRuanganAdminController {

    @FXML
    private TextField txtFasilitas;

    @FXML
    private TextField txtKapasitas;

    @FXML
    private TextField txtKeteranganRuangan;

    @FXML
    private Label lblNamaFile;

    @FXML
    private ComboBox<Gedung> cbLokasi;

    private String selectedImagePath = null; // Menyimpan path gambar
    private GedungDAO gedungDAO = new GedungDAO();
    private RuanganAdminDAO ruanganDAO = new RuanganAdminDAO();
    private File selectedFile;

    @FXML
    public void initialize() {
        loadDataGedung();
    }

    private void loadDataGedung() {
        // Ambil data dari database dan masukkan ke ComboBox
        List<Gedung> listGedung = gedungDAO.getAllGedung();
        cbLokasi.getItems().addAll(listGedung);
    }

    @FXML
    private TextField txtNamaRuangan;

    @FXML
    private TextField txtStatus;


    @FXML
    void btnBatal(ActionEvent event) {
        pindahHalaman("kelola_ruangan");
    }

    @FXML
    void btnPilihGambar(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Pilih Foto Ruangan");
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        Stage stage = (Stage) txtNamaRuangan.getScene().getWindow();
        this.selectedFile = fc.showOpenDialog(stage);

       
        if (this.selectedFile != null) {
            lblNamaFile.setText(this.selectedFile.getName());
        }
        

    }

    @FXML
    void btnTambahRuangan(ActionEvent event) {
        try {
            if (txtNamaRuangan.getText().isEmpty() || txtKapasitas.getText().isEmpty() || cbLokasi.getValue() == null) {
                tampilkanAlert(Alert.AlertType.WARNING, "Data Tidak Lengkap", "Mohon isi Nama, Kapasitas, dan Lokasi!");
                return;
            }

            

            Ruangan r = new Ruangan(0, "", "", 0, 0, "", "", ""); 

            r.setNamaRuangan(txtNamaRuangan.getText());
            r.setDeskripsi(txtKeteranganRuangan.getText()); //
            r.setKapasitas(Integer.parseInt(txtKapasitas.getText()));
            r.setStatus(txtStatus.getText());          
            r.setLantai(1); // Default lantai 1
            r.setTipeRuangan("Kelas"); // Default 
            if (this.selectedFile != null) {
                String pathBaru = uploadGambar(this.selectedFile);
                r.setFotoPath(pathBaru); 
            } else {
                r.setFotoPath(null); 
            }



            List<Fasilitas> listFasilitas = new ArrayList<>();
            String inputFas = txtFasilitas.getText();
            if (inputFas != null && !inputFas.isEmpty()) {
                String[] items = inputFas.split(","); // Pisah berdasarkan koma
                for (String item : items) {
                    // Buat object fasilitas baru
                    Fasilitas f = new Fasilitas(); 
                    f.setNamaFasilitas(item.trim()); // trim() untuk hapus spasi
                    listFasilitas.add(f);
                }
            }

            r.setListFasilitas(listFasilitas);
            
            // String lokasiInput = cbLokasi.getValue().toString();
            int idGedung = cbLokasi.getValue().getIdGedung();
            if (ruanganDAO.insertRuangan(r, idGedung)) {
                tampilkanAlert(Alert.AlertType.INFORMATION, "Berhasil", "Ruangan dan Fasilitas berhasil disimpan!");
                pindahHalaman("kelola_ruangan");
            } else {
                tampilkanAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menyimpan ke database.");
            }
  

        } catch (NumberFormatException e) {
            tampilkanAlert(Alert.AlertType.ERROR, "Input Salah", "Kapasitas harus berupa angka!");
        } catch (Exception e) {
            e.printStackTrace();
            tampilkanAlert(Alert.AlertType.ERROR, "Error", "Terjadi kesalahan: " + e.getMessage());
        }
    }

    private String uploadGambar(File source) throws IOException {
        String folderName = "src/main/resources/images"; 
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs(); 
        }

        // Buat nama file unik
        String newFileName = System.currentTimeMillis() + "_" + source.getName();
        File destination = new File(folder, newFileName);

        // Copy file
        Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return "/images/" + newFileName; 
    }




    private void tampilkanAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

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
        
        
        stage.setResizable(false); 
        stage.centerOnScreen();
        
    }




}
