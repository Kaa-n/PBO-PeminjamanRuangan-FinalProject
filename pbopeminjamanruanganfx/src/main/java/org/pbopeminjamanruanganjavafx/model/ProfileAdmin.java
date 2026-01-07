package org.pbopeminjamanruanganjavafx.model;

public class ProfileAdmin {
    private int idUser;
    private String nama;
    private String username;
    private String email;

    public ProfileAdmin(int idUser, String nama, String username, String email) {
        this.idUser = idUser;
        this.nama = nama;
        this.username = username;
        this.email = email;
    }

    // Getters and Setters
    public int getIdUser() { return idUser; }
    public String getNama() { return nama; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}