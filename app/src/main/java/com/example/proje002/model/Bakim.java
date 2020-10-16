package com.example.proje002.model;

public class Bakim {
    private long id;
    private String bakim1;
    private String bakim2;

    public Bakim(long id, String bakim1, String bakim2) {
        this.id = id;
        this.bakim1 = bakim1;
        this.bakim2 = bakim2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBakim1() {
        return bakim1;
    }

    public void setBakim1(String bakim1) {
        this.bakim1 = bakim1;
    }

    public String getBakim2() {
        return bakim2;
    }

    public void setBakim2(String bakim2) {
        this.bakim2 = bakim2;
    }
}
