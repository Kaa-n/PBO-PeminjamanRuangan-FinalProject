package org.pbopeminjamanruanganjavafx.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.pbopeminjamanruanganjavafx.model.Ruangan;
import org.pbopeminjamanruanganjavafx.model.User;
import org.pbopeminjamanruanganjavafx.util.UserSession;
import org.pbopeminjamanruanganjavafx.App;
import org.pbopeminjamanruanganjavafx.dao.PeminjamanUserDAO;
import org.pbopeminjamanruanganjavafx.model.Peminjam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FromPeminjamanController {

    @FXML
    private Label txtInformasiPemesanan;

    @FXML
    private TextField txtJam;

    @FXML
    private TextField txtJumlahPeserta;

    @FXML
    private Label txtKembaliKeJadwal;

    @FXML
    private TextField txtNamaLengkap;

    @FXML
    private TextField txtNomorKontak;

    @FXML
    private TextField txtRuangan;

    @FXML
    private TextField txtTanggal;

    @FXML
    private Label txtTanggalHariPeminjam;

    @FXML
    private TextField txtTujuanPeminjaman;

    @FXML
    private TextField txtNim;

    @FXML
    private TextArea txtCatatan;

    @FXML
    void btnBeranda(ActionEvent event) {
        pindahHalaman("dashboard_peminjam_new");
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

    @FXML
    void btnAkun(ActionEvent event) {
        pindahHalaman("Profile");
    }

    @FXML
    void btnKeluar(ActionEvent event) {
        try {
            pindahLogin(event, "login.fxml", "LOGIN SIPIRANG");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnBatal(ActionEvent event) {
        pindahHalaman("detail_ruangan_dan_jadwal_peminjam");
    }

    // @FXML
    // void btnKirimPengajuan(ActionEvent event) {
    //     pindahHalaman("ruangan_peminjam");

    // }

    @FXML
    void btnKembali(ActionEvent event) {
        kembaliKeDaftarRuangan();
    }

    @FXML
    void pindahHalaman(String namaFxml) {
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



    private PeminjamanUserDAO peminjamanUserDAO = new PeminjamanUserDAO();

    private Ruangan ruanganTerpilih;
    private LocalDate tanggalTerpilih;
    // final times used for DAO (either from standard schedule or manual input)
    private LocalTime jamMulaiFinal;
    private LocalTime jamSelesaiFinal;
    // DAO instance used to persist reservation
    private PeminjamanUserDAO reservasiDAO = new PeminjamanUserDAO();

    private boolean isKhusus;

    // --- METHOD INI YANG AKAN DIPANGGIL DARI DETAIL RUANGAN ---
    public void setDataPeminjaman(Ruangan ruangan, LocalDate tanggal, LocalTime mulai, LocalTime selesai, boolean khusus) {
        this.ruanganTerpilih = ruangan;
        this.tanggalTerpilih = tanggal;
        // assign final times by default; will be overwritten if user enters manual waktu khusus

        // 1. Ambil ID User yang sedang login
        int currentUserId = UserSession.getUser().getIdUser();


        // 2. Ambil data dari Database
        PeminjamanUserDAO.DataPemohon data = peminjamanUserDAO.getDetailPemohon(currentUserId);

        if (data != null) {
            // Isi Nama
            if (data.nama != null && !data.nama.isEmpty()) {
                txtNamaLengkap.setText(data.nama);
                txtNamaLengkap.setDisable(true); // Kunci agar tidak bisa diubah (Opsional)
            }

            // Isi NIM
            if (data.nim != null && !data.nim.isEmpty()) {
                // Pastikan txtNim tidak null (cek FXML)
                if (txtNim != null) {
                    txtNim.setText(data.nim);
                    txtNim.setDisable(true);
                }
            }
            
            // Catatan untuk Nomor Kontak:
            // Karena di database tabel 'user' dan 'peminjam' belum ada kolom no_hp,
            // field ini kita biarkan kosong agar user mengisi manual.
        }


        this.jamMulaiFinal = mulai;
        this.jamSelesaiFinal = selesai;
        this.isKhusus = khusus;

        // 1. Isi & Kunci Ruangan
        txtRuangan.setText(ruangan.getNamaRuangan());
        txtRuangan.setDisable(true); // Read-only

        // 2. Isi & Kunci Tanggal (Format: 12-01-2026)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtTanggal.setText(tanggal.format(fmt));
        txtTanggal.setDisable(true); // Read-only

        // 3. Logika Jam (Standard vs Khusus)
        if (isKhusus) {
            // Mode Khusus: Kosongkan dan biarkan user mengetik
            txtJam.setText("");
            txtJam.setPromptText("Contoh: 07.00 - 15.00");
            txtJam.setDisable(false); // BISA DIEDIT
            txtJam.setEditable(true);
        } else {
            // Mode Normal: Isi otomatis dan kunci
            String range = mulai + " - " + selesai;
            txtJam.setText(range);
            txtJam.setDisable(true); // TIDAK BISA DIEDIT
        }
        
        // (Opsional) Auto-fill kapasitas max
        txtJumlahPeserta.setPromptText("Max: " + ruangan.getKapasitas());
    }

    @FXML
    private void handleSimpan() {
        // Logika simpan ke database...
        // Jika isKhusus == true, ambil string jam mentah dari txtJam.getText()
        // Jika isKhusus == false, pakai variabel jamMulai & jamSelesai
    }

    @FXML
    void btnKirimPengajuan(ActionEvent event) {
        // 1. Validasi Input UI
        if (txtJumlahPeserta.getText().isEmpty() || txtTujuanPeminjaman.getText().isEmpty() ||
            txtNamaLengkap.getText().isEmpty() || txtNomorKontak.getText().isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Harap lengkapi semua field yang berbintang (*).");
            return;
        }

        // 2. Validasi Jam Manual (Jika Mode Khusus)
        if (isKhusus) {
            if (!prosesJamManual(txtJam.getText())) {
                return; 
            }
        }

        // 3. Persiapan Data untuk DAO
        int idPeminjamDummy = 1; // Nanti ganti dengan Session User
        int idRuangan = ruanganTerpilih.getIdRuangan();
        int idTujuan = 1; // Nanti ganti dengan ComboBox ID
        int jumlahPeserta = 0;
        
        try {
            jumlahPeserta = Integer.parseInt(txtJumlahPeserta.getText());
        } catch (NumberFormatException e) {
            tampilkanAlert(Alert.AlertType.ERROR, "Error", "Jumlah peserta harus angka!");
            return;
        }

        String noTelepon = txtNomorKontak.getText();
        String keterangan = (txtCatatan != null ? txtCatatan.getText() : "");
 
        // 4. PANGGIL DAO UNTUK SIMPAN KE DATABASE
        boolean berhasil = reservasiDAO.simpanReservasi(
            idPeminjamDummy, idRuangan, idTujuan, jumlahPeserta,
            tanggalTerpilih, jamMulaiFinal, jamSelesaiFinal,
            noTelepon, keterangan
        );

        // 5. Cek Hasil
        if (berhasil) {
            tampilkanAlert(Alert.AlertType.INFORMATION, "Berhasil", "Pengajuan berhasil dikirim! Menunggu persetujuan admin.");
            kembaliKeDaftarRuangan();
        } else {
            tampilkanAlert(Alert.AlertType.ERROR, "Gagal", "Terjadi kesalahan saat menyimpan data ke database.");
        }
    }

    @FXML
    void handleBatal(ActionEvent event) {
        kembaliKeDaftarRuangan();
    }

    // Helper: Logic UI Parsing Jam (Tetap di Controller karena ini urusan format inputan user)
    private boolean prosesJamManual(String inputJam) {
        try {
            String cleanInput = inputJam.replace(".", ":");
            String[] parts = cleanInput.split("-");
 
            if (parts.length != 2) throw new Exception("Format salah");
 
            this.jamMulaiFinal = LocalTime.parse(parts[0].trim());
            this.jamSelesaiFinal = LocalTime.parse(parts[1].trim());
            
             return true;
         } catch (Exception e) {
             tampilkanAlert(Alert.AlertType.ERROR, "Format Jam Salah", 
                 "Gunakan format: HH.mm - HH.mm\nContoh: 08.00 - 15.00");
             return false;
         }
     }
 
    private void kembaliKeDaftarRuangan() {
        try {
            App.setRoot("ruangan_peminjam"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tampilkanAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
