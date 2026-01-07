package org.pbopeminjamanruanganjavafx.model;

import java.util.ArrayList;
import java.util.List;

public class Ruangan {
    private int idRuangan;
    private String namaRuangan;
    private String deskripsi;
    private int kapasitas;
    private int lantai;
    private String status;
    private String fotoPath;
    private String tipeRuangan;
    
    // Relasi Objek
    private Gedung gedung; 
    private List<Fasilitas> listFasilitas; 

    public Ruangan(int idRuangan, String namaRuangan, String deskripsi, int kapasitas, int lantai, String status, String fotoPath, String tipeRuangan) {
        this.idRuangan = idRuangan;
        this.namaRuangan = namaRuangan;
        this.deskripsi = deskripsi;
        this.kapasitas = kapasitas;
        this.lantai = lantai;
        this.tipeRuangan = tipeRuangan;
        this.status = status;
        this.fotoPath = fotoPath;
        this.listFasilitas = new ArrayList<>(); // Inisialisasi list kosong agar tidak error jika null
    }

    // Setters & Getters Relasi 
    public void setGedung(Gedung gedung) {
        this.gedung = gedung;
    }

    public Gedung getGedung() {
        return gedung;
    }

    public void setListFasilitas(List<Fasilitas> listFasilitas) {
        this.listFasilitas = listFasilitas;
    }

    public List<Fasilitas> getListFasilitas() {
        return listFasilitas;
    }

    // Getter & Setter Biasa
    public int getIdRuangan() { return idRuangan; }
    public String getNamaRuangan() { return namaRuangan; }
    public String getDeskripsi() { return deskripsi; }
    public int getKapasitas() { return kapasitas; }
    public int getLantai() { return lantai; }
    public String getStatus() { return status; }
    public String getFotoPath() { return fotoPath; }
    public String getTipeRuangan() { return tipeRuangan; }
}
