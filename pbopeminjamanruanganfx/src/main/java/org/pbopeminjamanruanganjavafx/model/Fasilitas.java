package org.pbopeminjamanruanganjavafx.model;

public class Fasilitas {
    private int idFasilitas;
    private String namaFasilitas;
    private int jumlah;
    private String kondisi;

    public Fasilitas(int idFasilitas, String namaFasilitas, int jumlah, String kondisi) {
        this.idFasilitas = idFasilitas;
        this.namaFasilitas = namaFasilitas;
        this.jumlah = jumlah;
        this.kondisi = kondisi;
    }

    public String getNamaFasilitas() { return namaFasilitas; }
    public int getJumlah() { return jumlah; }
    public String getKondisi() { return kondisi; }
}
