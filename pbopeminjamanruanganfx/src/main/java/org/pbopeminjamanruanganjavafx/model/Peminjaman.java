package org.pbopeminjamanruanganjavafx.model;

public class Peminjaman {
    private int id;
    private String namaPeminjam;
    private String namaRuangan;
    private String tanggal;
    private String jam;
    private int jumlahPeserta;
    private String status;
    private String note;

    public Peminjaman(int id, String namaPeminjam, String namaRuangan, String tanggal, String jam, int jumlahPeserta,
            String status, String note) {
        this.id = id;
        this.namaPeminjam = namaPeminjam;
        this.namaRuangan = namaRuangan;
        this.tanggal = tanggal;
        this.jam = jam;
        this.jumlahPeserta = jumlahPeserta;
        this.status = status;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    public int getJumlahPeserta() {
        return jumlahPeserta;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }
}