package org.pbopeminjamanruanganjavafx.controller;

import java.net.URL; // Penting!
import java.util.ResourceBundle;

import org.pbopeminjamanruanganjavafx.dao.PeminjamanUserDAO;
import org.pbopeminjamanruanganjavafx.model.Peminjaman;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StatusController implements Initializable {

    @FXML private TableView<Peminjaman> tabelStatus;
    
    // Pastikan tipe generic keduanya <Peminjaman, String> karena kita akan ubah semua jadi teks
    @FXML private TableColumn<Peminjaman, String> colNamaRuangan;
    @FXML private TableColumn<Peminjaman, String> colTanggal;
    @FXML private TableColumn<Peminjaman, String> colJam;
    @FXML private TableColumn<Peminjaman, String> colJumlahPeserta;
    @FXML private TableColumn<Peminjaman, String> colKontak; 
    @FXML private TableColumn<Peminjaman, String> colNote;

    private ObservableList<Peminjaman> listPeminjaman = FXCollections.observableArrayList();
    private PeminjamanUserDAO peminjamanDAO = new PeminjamanUserDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // --- CARA BINDING DATA POJO (Non-Property) ---
        
        // 1. Nama Ruangan (Ambil dari getNamaRuangan)
        colNamaRuangan.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getNamaRuangan()));

        // 2. Tanggal
        colTanggal.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getTanggal()));

        // 3. Jam
        colJam.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getJam()));

        // 4. Jumlah Peserta (Int diubah jadi String + " Orang")
        colJumlahPeserta.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getJumlahPeserta() + " Orang"));

        // 5. Note / Keterangan
        colNote.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getNote()));

        // 6. Kontak (Masalah: Di Model Anda BELUM ADA getter untuk kontak)
        // Sementara kita isi strip "-" dulu agar tidak error
        colKontak.setCellValueFactory(data -> new SimpleStringProperty("-"));

        // --- LOAD DATA ---
        loadData();
        tabelStatus.setItems(listPeminjaman);
    }

    private void loadData() {
        listPeminjaman.clear();
        // Ganti ID user sesuai user yang login nanti
        listPeminjaman.addAll(peminjamanDAO.getPeminjamanSaya(1)); 
    }
}