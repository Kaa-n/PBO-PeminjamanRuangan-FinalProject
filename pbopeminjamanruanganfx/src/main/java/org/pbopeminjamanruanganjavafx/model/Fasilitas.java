package org.pbopeminjamanruanganjavafx.model;

public class Fasilitas {
    private int idFasilitas;
    private String namaFasilitas;
    private int jumlah;
    private String kondisi;

    public Fasilitas() {
    }

    public Fasilitas(int idFasilitas, String namaFasilitas, int jumlah, String kondisi) {
        this.idFasilitas = idFasilitas;
        this.namaFasilitas = namaFasilitas;
        this.jumlah = jumlah;
        this.kondisi = kondisi;
    }

    public String getNamaFasilitas() { return namaFasilitas; }
    public void setNamaFasilitas(String namaFasilitas) { this.namaFasilitas = namaFasilitas; }
    public int getJumlah() { return jumlah; }
    public String getKondisi() { return kondisi; }

    public int getIdFasilitas() { return idFasilitas; }
    public void setIdFasilitas(int idFasilitas) { this.idFasilitas = idFasilitas; }
}
