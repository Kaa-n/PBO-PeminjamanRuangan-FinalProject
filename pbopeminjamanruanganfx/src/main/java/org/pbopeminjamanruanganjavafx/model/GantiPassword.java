package org.pbopeminjamanruanganjavafx.model;

public class GantiPassword {
    private int idUser;
    private String passwordHashLama;
    private String passwordHashBaru;

    public GantiPassword(int idUser, String passwordHashLama, String passwordHashBaru) {
        this.idUser = idUser;
        this.passwordHashLama = passwordHashLama;
        this.passwordHashBaru = passwordHashBaru;
    }

    // Getter
    public int getIdUser() { return idUser; }
    public String getPasswordHashLama() { return passwordHashLama; }
    public String getPasswordHashBaru() { return passwordHashBaru; }
}