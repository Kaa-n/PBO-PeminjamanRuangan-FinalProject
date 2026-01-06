package org.pbopeminjamanruanganjavafx.model;

public class Peminjam extends User {
    private String prodi;
    public Peminjam(int idUser, String username, String role, String prodi) {
        super(idUser, username, role);
        this.prodi = prodi;
    }

    public String getProdi() {
        return prodi;
    }

    @Override
    public String getDashboardFxml() {
        return "dashboard_peminjam_new.fxml"; 
    }

    @Override
    public String getDashboardTitle() {
        return "Peminjam SIPIRANG";
    }
}