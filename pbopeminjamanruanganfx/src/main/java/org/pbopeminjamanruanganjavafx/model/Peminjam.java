package org.pbopeminjamanruanganjavafx.model;

public class Peminjam extends User {
    public Peminjam(int idUser, String username, String role) {
        super(idUser, username, role);
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