package org.pbopeminjamanruanganjavafx.model;

public class KelolaRuangan {

    private int idRuangan;
    private String namaRuangan;
    private int kapasitas;
    private String statusRuangan;
    private String tipeRuangan;
    private String keteranganRuangan;
    private String fotoRuangan;

    public KelolaRuangan(int idRuangan, String namaRuangan, int kapasitas,
                   String statusRuangan, String tipeRuangan,
                   String keteranganRuangan, String fotoRuangan) {
        this.idRuangan = idRuangan;
        this.namaRuangan = namaRuangan;
        this.kapasitas = kapasitas;
        this.statusRuangan = statusRuangan;
        this.tipeRuangan = tipeRuangan;
        this.keteranganRuangan = keteranganRuangan;
        this.fotoRuangan = fotoRuangan;
    }

    public int getIdRuangan() {
        return idRuangan;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public String getStatusRuangan() {
        return statusRuangan;
    }

    public String getTipeRuangan() {
        return tipeRuangan;
    }

    public String getKeteranganRuangan() {
        return keteranganRuangan;
    }

    public String getFotoRuangan() {
        return fotoRuangan;
    }
}