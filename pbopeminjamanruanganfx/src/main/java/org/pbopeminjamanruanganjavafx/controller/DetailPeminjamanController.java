package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.PeminjamanAdminDAO; // Import DAO Admin
import org.pbopeminjamanruanganjavafx.model.Peminjaman; // Import Model

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class DetailPeminjamanController implements Initializable {

    // --- BAGIAN INI SAYA TAMBAHKAN AGAR SESUAI FXML ---
    @FXML
    private TableColumn<Peminjaman, String> colNamaRuangan;
    @FXML
    private TableColumn<Peminjaman, String> colTanggal;
    @FXML
    private TableColumn<Peminjaman, String> colJam;
    @FXML
    private TableColumn<Peminjaman, String> colJumlahPeserta;
    @FXML
    private TableColumn<Peminjaman, String> colKontak;
    @FXML
    private TableColumn<Peminjaman, String> colNote;
    @FXML
    private TableColumn<Peminjaman, String> colStatus;

    // Variabel Data dan DAO
    private ObservableList<Peminjaman> listPeminjaman = FXCollections.observableArrayList();
    private PeminjamanAdminDAO adminDAO = new PeminjamanAdminDAO();

    // --- BAGIAN KODE ANDA (TIDAK DIHAPUS, HANYA DIPERBAIKI TIPE DATA TABELNYA) ---

    @FXML
    private Label lblKegiatan;

    @FXML
    private Label lblPeminjam;

    @FXML
    private TableView<Peminjaman> tblDetailPeminjaman; // Saya ubah <?> jadi <Peminjaman> agar tidak error

    // --- IMPLEMENTASI INITIALIZE (WAJIB ADA) ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Setup Kolom Tabel
        colNamaRuangan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaRuangan()));
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggal()));
        colJam.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJam()));
        colJumlahPeserta
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJumlahPeserta() + " Orang"));
        colKontak.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKontak()));
        colNote.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNote()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        colStatus.setCellFactory(column -> new javafx.scene.control.TableCell<Peminjaman, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equalsIgnoreCase("Disetujui")) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else if (item.equalsIgnoreCase("Ditolak")) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    }
                }
            }
        });

        // 2. Load Data
        loadData();
        tblDetailPeminjaman.setItems(listPeminjaman);

        // 3. Listener: Update Label Header saat baris diklik
        tblDetailPeminjaman.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblPeminjam.setText(newVal.getNamaPeminjam());
                lblKegiatan.setText(newVal.getNote());
            } else {
                lblPeminjam.setText("-");
                lblKegiatan.setText("Pilih data tabel");
            }
        });
    }

    private void loadData() {
        listPeminjaman.clear();
        
        listPeminjaman.addAll(adminDAO.getAllPeminjaman());
    }

    @FXML
    private void btnAkun() throws IOException {
        try {
            App.setRoot("profile_admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnBeranda() throws IOException {
        try {
            App.setRoot("dashboard_admin_new");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKelolaPeminjaman(ActionEvent event) {
        loadData();
    }

    @FXML
    private void btnKelolaRuangan(ActionEvent event) {
        try {
            App.setRoot("kelola_ruangan");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKelolaUser(ActionEvent event) {
        try {
            App.setRoot("kelola_user");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnKeluar(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("LOGIN SIPIRANG");

         
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKontak(ActionEvent event) {
        try {
            App.setRoot("kontak_admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- LOGIKA TOMBOL AKSI  ---

    @FXML
    void btnSetuju(ActionEvent event) {
        processStatusUpdate("Disetujui");
    }

    @FXML
    void btnTolak(ActionEvent event) {
        processStatusUpdate("Ditolak");
    }

    private void processStatusUpdate(String newStatus) {
        Peminjaman selected = tblDetailPeminjaman.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Peringatan", "Pilih data peminjaman yang ingin diproses!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi");
        confirm.setHeaderText(null);
        confirm.setContentText("Ubah status " + selected.getNamaPeminjam() + " menjadi " + newStatus + "?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            boolean sukses = adminDAO.updateStatusReservasi(selected.getId(), newStatus);

            if (sukses) {
                showAlert("Sukses", "Status berhasil diubah menjadi: " + newStatus, Alert.AlertType.INFORMATION);
                loadData(); // Refresh tabel

                // Reset header
                lblPeminjam.setText("-");
                lblKegiatan.setText("Pilih data tabel");
            } else {
                showAlert("Gagal", "Gagal mengupdate status.", Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}