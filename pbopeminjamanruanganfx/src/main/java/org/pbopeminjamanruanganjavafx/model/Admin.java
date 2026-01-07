package org.pbopeminjamanruanganjavafx.model;

public class Admin extends User {
    public Admin(int idUser, String username, String role) {
        super(idUser, username, role);
    }

    @Override
    public String getDashboardFxml() {
        return "dashboard_admin_new.fxml";
    }

    @Override
    public String getDashboardTitle() {
        return "Admin SIPIRANG";
    }
    
}
