package com.example.lina.beratbadanapp.model;

public class BMImodel {
    public BMImodel() {
    }

    private int id;
    private String nama;
    private String jeniskelamin;
    private String berat_badan;
    private String tinggibadan;
    private String bmi;
    private String hasil;
    private String keterangan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(String berat_badan) {
        this.berat_badan = berat_badan;
    }

    public String getTinggibadan() {
        return tinggibadan;
    }

    public void setTinggibadan(String tinggibadan) {
        this.tinggibadan = tinggibadan;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public BMImodel( String nama, String jeniskelamin, String berat_badan, String tinggibadan, String bmi, String hasil, String keterangan) {

        this.nama = nama;
        this.jeniskelamin = jeniskelamin;
        this.berat_badan = berat_badan;
        this.tinggibadan = tinggibadan;
        this.bmi = bmi;
        this.hasil = hasil;
        this.keterangan = keterangan;
    }
}
