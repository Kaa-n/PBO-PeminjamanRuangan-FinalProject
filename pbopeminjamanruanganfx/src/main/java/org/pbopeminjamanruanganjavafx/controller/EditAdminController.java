package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.GedungDAO;
import org.pbopeminjamanruanganjavafx.dao.RuanganAdminDAO;
import org.pbopeminjamanruanganjavafx.model.Fasilitas;
import org.pbopeminjamanruanganjavafx.model.Gedung;
import org.pbopeminjamanruanganjavafx.model.Ruangan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class EditAdminController {

    @FXML private TextField txtNamaRuangan;
    @FXML private TextField txtKapasitas;
    @FXML private TextField txtFasilitas;
    @FXML private TextField txtStatus;
    @FXML private TextField txtKeteranganRuangan; 
    
    @FXML private ComboBox<Gedung> cbLokasi; 
    
    @FXML
    private Label lblNamaFile; 

    private Ruangan currentRuangan; 
    private String selectedImagePath = null; 
    
    private GedungDAO gedungDAO = new GedungDAO();
    private RuanganAdminDAO ruanganDAO = new RuanganAdminDAO();

    @FXML
    public void initialize() {
        loadDataGedung();
    }

    private void loadDataGedung() {
        List<Gedung> list = gedungDAO.getAllGedung();
        cbLokasi.getItems().addAll(list);
    }
    
    public void setRuanganData(Ruangan ruangan) {
        this.currentRuangan = ruangan;
        this.selectedImagePath = ruangan.getFotoPath(); // Default path lama

        // Isi TextFields
        txtNamaRuangan.setText(ruangan.getNamaRuangan());
        txtKapasitas.setText(String.valueOf(ruangan.getKapasitas()));
        txtStatus.setText(ruangan.getStatus());
        if (txtKeteranganRuangan != null) txtKeteranganRuangan.setText(ruangan.getDeskripsi());

        if (ruangan.getListFasilitas() != null && !ruangan.getListFasilitas().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ruangan.getListFasilitas().size(); i++) {
                sb.append(ruangan.getListFasilitas().get(i).getNamaFasilitas());
                // Tambah koma jika bukan item terakhir
                if (i < ruangan.getListFasilitas().size() - 1) {
                    sb.append(", ");
                }
            }
            txtFasilitas.setText(sb.toString());
        } else {
            txtFasilitas.setText("");
        }
        
        // Pilih item ComboBox yang sesuai
        int targetIdGedung = ruangan.getIdGedung(); 
        
        if (cbLokasi.getItems() != null) {
            for (Gedung g : cbLokasi.getItems()) {
                if (g.getIdGedung() == targetIdGedung) {
                    cbLokasi.setValue(g); 
                    break; 
                }
            }
        }
        
        if (lblNamaFile != null && selectedImagePath != null) {
            lblNamaFile.setText("Gambar tersimpan (Klik pilih untuk ganti)");
        }
    }


    @FXML
    void btnBatal(ActionEvent event) {
        pindahHalaman("kelola_ruangan");
    }
    @FXML
    void btnPilihGambar(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Ganti Foto Ruangan");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        
        Stage stage = (Stage) txtNamaRuangan.getScene().getWindow();
        File file = fc.showOpenDialog(stage);

        if (file != null) {
            selectedImagePath = file.toURI().toString();
            if (lblNamaFile != null) lblNamaFile.setText(file.getName());
        }

    }

    @FXML
    void btnSimpanPerubahan(ActionEvent event) {
        try {
            if (txtNamaRuangan.getText().isEmpty() || cbLokasi.getValue() == null) {
                tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Data utama tidak boleh kosong!");
                return;
            }

            currentRuangan.setNamaRuangan(txtNamaRuangan.getText());
            currentRuangan.setKapasitas(Integer.parseInt(txtKapasitas.getText()));
            currentRuangan.setStatus(txtStatus.getText());
            if (txtKeteranganRuangan != null) currentRuangan.setDeskripsi(txtKeteranganRuangan.getText());
            
            // Set Path Gambar
            currentRuangan.setFotoPath(selectedImagePath);

            List<Fasilitas> listFasilitasBaru = new ArrayList<>();
            String inputFas = txtFasilitas.getText();
            
            if (inputFas != null && !inputFas.isEmpty()) {
                String[] items = inputFas.split(","); 
                for (String item : items) {
                    Fasilitas f = new Fasilitas();
                    f.setNamaFasilitas(item.trim());
                    listFasilitasBaru.add(f);
                }
            }
            currentRuangan.setListFasilitas(listFasilitasBaru);

            int idGedung = cbLokasi.getValue().getIdGedung();

            // Panggil DAO Update
            if (ruanganDAO.updateRuangan(currentRuangan, idGedung)) {
                tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil diperbarui!");
                pindahHalaman("kelola_ruangan");
            } else {
                tampilkanAlert(Alert.AlertType.ERROR, "Gagal", "Gagal update database.");
            }

        } catch (NumberFormatException e) {
            tampilkanAlert(Alert.AlertType.ERROR, "Error", "Kapasitas harus angka!");
        }
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
        pindahHalaman("admin_profile");
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

    
}
