package org.pbopeminjamanruanganjavafx.model;

public class Admin extends User {
    public Admin(int idUser, String username, String role) {
        super(idUser, username, role);
    }
    //metode khusus admin ditambahkan di sini

    @Override
    public String getDashboardFxml() {
        return "dashboard_admin_new.fxml";
    }

    @Override
    public String getDashboardTitle() {
        return "Dashboard Admin - SIPIRANG";
    }
    
}
