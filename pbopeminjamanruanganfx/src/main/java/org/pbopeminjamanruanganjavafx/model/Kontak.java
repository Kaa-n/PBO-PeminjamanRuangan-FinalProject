package org.pbopeminjamanruanganjavafx.model;

import java.time.LocalDateTime;

public class Kontak {

    private int idKontak;
    private Integer idUser;
    private String nama;
    private String email;
    private String subjek;
    private String pesan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Kontak() {
        
    }

    public Kontak(Integer idUser, String nama, String email, String subjek, String pesan) {
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.subjek = subjek;
        this.pesan = pesan;
    }
    public int getIdKontak() {
        return idKontak;
    }

    public void setIdKontak(int idKontak) {
        this.idKontak = idKontak;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjek() {
        return subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
