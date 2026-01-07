package org.pbopeminjamanruanganjavafx.model;

import java.time.LocalTime;

public class Gedung {
    private int idGedung;
    private String namaGedung;
    private String jamBuka;  // Format "HH:mm:ss" dari Time SQL
    private String jamTutup;

    public Gedung(int idGedung, String namaGedung, String jamBuka, String jamTutup) {
        this.idGedung = idGedung;
        this.namaGedung = namaGedung;
        this.jamBuka = jamBuka;
        this.jamTutup = jamTutup;
    }

    // Getter & Setter
    public int getIdGedung() { 
        return idGedung; }
    public void setIdGedung(int idGedung) { 
        this.idGedung = idGedung; }
    public String getNamaGedung() { 
        return namaGedung; }
    public void setNamaGedung(String namaGedung) { 
        this.namaGedung = namaGedung; }
    public String getJamBuka() { 
        return jamBuka; }
    public void setJamBuka(String jamBuka) { 
        this.jamBuka = jamBuka; }
    public String getJamTutup() { return 
        jamTutup; }
    public void setJamTutup(String jamTutup) { 
        this.jamTutup = jamTutup; }
    
    // public boolean isBukaPadaJam(LocalTime waktu) {
    //     return !waktu.isBefore(jamBuka) && !waktu.isAfter(jamTutup);
    // }
    
    @Override
    public String toString() {
        return namaGedung; 
    }
}
