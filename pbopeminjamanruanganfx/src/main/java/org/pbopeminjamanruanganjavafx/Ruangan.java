package org.pbopeminjamanruanganjavafx;

// Model kelas untuk Ruangan sebagai percobaan
public class Ruangan {
    private String nama;
    private String deskripsi;
    private int kapasitas;
    private String status;
    private String fasilitas; // Disimpan sebagai String dipisah koma
    private String fotoPath;

    // Constructor
    public Ruangan(String nama, String deskripsi, int kapasitas, String status, String fasilitas, String fotoPath) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.kapasitas = kapasitas;
        this.status = status;
        this.fasilitas = fasilitas;
        this.fotoPath = fotoPath;
    }

    // Getters
    public String getNama() { return nama; }
    public String getDeskripsi() { return deskripsi; }
    public int getKapasitas() { return kapasitas; }
    public String getStatus() { return status; }
    public String getFasilitas() { return fasilitas; }
    public String getFotoPath() { return fotoPath; }
}
