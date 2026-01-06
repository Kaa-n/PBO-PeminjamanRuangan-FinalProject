package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL; // Penting!
import java.util.Optional;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.PeminjamanUserDAO;
import org.pbopeminjamanruanganjavafx.model.Peminjaman;

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

public class StatusController implements Initializable {

    @FXML
    private TableView<Peminjaman> tabelStatus;

    @FXML
    private Label lblPeminjam;
    @FXML
    private Label lblKegiatan;

    // Pastikan tipe generic keduanya <Peminjaman, String> karena kita akan ubah
    // semua jadi teks
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

    private ObservableList<Peminjaman> listPeminjaman = FXCollections.observableArrayList();
    private PeminjamanUserDAO peminjamanDAO = new PeminjamanUserDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // --- CARA BINDING DATA POJO (Non-Property) ---

        // 1. Nama Ruangan (Ambil dari getNamaRuangan)
        colNamaRuangan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaRuangan()));

        // 2. Tanggal
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggal()));

        // 3. Jam
        colJam.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJam()));

        // 4. Jumlah Peserta (Int diubah jadi String + " Orang")
        colJumlahPeserta
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJumlahPeserta() + " Orang"));

        // 5. Note / Keterangan
        colNote.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNote()));

        // 6. Kontak (Masalah: Di Model Anda BELUM ADA getter untuk kontak)
        // Sementara kita isi strip "-" dulu agar tidak error
        colKontak.setCellValueFactory(data -> new SimpleStringProperty("-"));

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

                    // Cek Status dan Ganti Warna
                    if (item.equalsIgnoreCase("Disetujui")) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else if (item.equalsIgnoreCase("Ditolak")) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        // Untuk status "Menunggu" atau "Selesai"
                        setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    }
                }
            }
        });

        // --- LOAD DATA ---
        loadData();
        tabelStatus.setItems(listPeminjaman);
    }

    private void loadData() {
        listPeminjaman.clear();
        // TODO: Ganti angka 3 dengan UserSession.getUserId() nanti
        listPeminjaman.addAll(peminjamanDAO.getPeminjamanSaya(3));

        // Panggil refresh header setiap kali data dimuat
        refreshHeader();
    }

    @FXML
    private void btnAkun() throws IOException {
        try {
            App.setRoot("Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnStatus(ActionEvent event) {
        try {
            App.setRoot("user_detail_peminjaman");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnBeranda() throws IOException {
        try {
            App.setRoot("dashboard_peminjam_new");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRuangan() throws IOException {
        try {
            App.setRoot("ruangan_peminjam");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");

            // Kembalikan kemampuan resize window untuk dashboard
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnKontak(ActionEvent event) {
        try {
            App.setRoot("kontak_user");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void btnBatalkanPeminjaman(ActionEvent event) {
        // 1. Ambil data terpilih
        Peminjaman selectedData = tabelStatus.getSelectionModel().getSelectedItem();

        if (selectedData == null) {
            showAlert("Peringatan", "Pilih dulu data yang ingin dibatalkan!", Alert.AlertType.WARNING);
            return;
        }

        // --- TAMBAHAN LOGIKA VALIDASI STATUS ---
        // Kita cek: Apakah statusnya BUKAN "Menunggu"?
        // Gunakan equalsIgnoreCase agar tidak peduli huruf besar/kecil
        if (!"Menunggu".equalsIgnoreCase(selectedData.getStatus())) {

            // Jika statusnya Disetujui, Ditolak, atau Selesai -> Tampilkan Error
            showAlert("Akses Ditolak",
                    "Anda tidak dapat membatalkan peminjaman ini karena statusnya sudah: " + selectedData.getStatus() +
                            ".\nHanya peminjaman berstatus 'Menunggu' yang dapat dibatalkan.",
                    Alert.AlertType.ERROR);
            return; // BERHENTI DI SINI (Jangan lanjut ke proses hapus)
        }
        // ---------------------------------------

        // 2. Konfirmasi (Hanya jalan jika status == Menunggu)
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Pembatalan");
        confirm.setHeaderText(null);
        confirm.setContentText(
                "Apakah Anda yakin ingin membatalkan pengajuan ruangan " + selectedData.getNamaRuangan() + "?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            boolean sukses = peminjamanDAO.batalkanPeminjaman(selectedData.getId());

            if (sukses) {
                listPeminjaman.remove(selectedData);
                refreshHeader(); // Update label header
                showAlert("Sukses", "Pengajuan peminjaman berhasil dibatalkan.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Gagal", "Terjadi kesalahan saat membatalkan peminjaman.", Alert.AlertType.ERROR);
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

    private void refreshHeader() {
        if (!listPeminjaman.isEmpty()) {
            // Ambil data urutan pertama (index 0)
            Peminjaman terbaru = listPeminjaman.get(0);

            lblPeminjam.setText(terbaru.getNamaPeminjam());
            lblKegiatan.setText(terbaru.getNote());
        } else {
            // Jika data kosong
            lblPeminjam.setText("-");
            lblKegiatan.setText("Tidak ada aktivitas");
        }
    }
}