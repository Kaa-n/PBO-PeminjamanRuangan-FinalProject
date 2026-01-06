package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.PeminjamanUserDAO;
import org.pbopeminjamanruanganjavafx.model.Peminjaman;
import org.pbopeminjamanruanganjavafx.util.UserSession;

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

        colNamaRuangan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaRuangan()));
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTanggal()));
        colJam.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJam()));
        colJumlahPeserta
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJumlahPeserta() + " Orang"));
        colNote.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNote()));
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

        loadData();
        tabelStatus.setItems(listPeminjaman);
    }

    private void loadData() {
        listPeminjaman.clear();

        if (UserSession.getUser() != null) {
            listPeminjaman.addAll(peminjamanDAO.getPeminjamanSaya(UserSession.getUser().getIdUser()));
        }

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
            UserSession.clear();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");

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
        Peminjaman selectedData = tabelStatus.getSelectionModel().getSelectedItem();

        if (selectedData == null) {
            showAlert("Peringatan", "Pilih dulu data yang ingin dibatalkan!", Alert.AlertType.WARNING);
            return;
        }

        if (!"Menunggu".equalsIgnoreCase(selectedData.getStatus())) {
            showAlert("Akses Ditolak",
                    "Anda tidak dapat membatalkan peminjaman ini karena statusnya sudah: " + selectedData.getStatus() +
                            ".\nHanya peminjaman berstatus 'Menunggu' yang dapat dibatalkan.",
                    Alert.AlertType.ERROR);
            return;
        }

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
                refreshHeader();
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
            Peminjaman terbaru = listPeminjaman.get(0);

            lblPeminjam.setText(terbaru.getNamaPeminjam());
            lblKegiatan.setText(terbaru.getNote());
        } else {
            lblPeminjam.setText("-");
            lblKegiatan.setText("Tidak ada aktivitas");
        }
    }
}