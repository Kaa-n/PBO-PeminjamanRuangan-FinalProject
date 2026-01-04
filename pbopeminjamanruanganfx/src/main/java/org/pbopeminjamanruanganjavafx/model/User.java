package org.pbopeminjamanruanganjavafx.model;

public abstract class User {
    protected int idUser;
    protected String username;
    protected String role; 

    // Constructor
    public User(int idUser, String username, String role) {
        this.idUser = idUser;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getRole() { return role; }
    public String getUsername() { return username; }
    public int getIdUser() { return idUser; }

    public abstract String getDashboardFxml();
    public abstract String getDashboardTitle();

}
