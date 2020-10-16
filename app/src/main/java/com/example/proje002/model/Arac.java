package com.example.proje002.model;

public class Arac {


    private long id;
    private String pilaka;
    private String marka;

    public Arac(long id, String pilaka, String marka) {
        this.id = id;
        this.pilaka = pilaka;
        this.marka = marka;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPilaka() {
        return pilaka;
    }

    public void setPilaka(String pilaka) {
        this.pilaka = pilaka;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }
}
