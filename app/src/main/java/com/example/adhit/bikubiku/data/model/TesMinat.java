package com.example.adhit.bikubiku.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TesMinat {
    @SerializedName("id_soal")
    @Expose
    private String idSoal;
    @SerializedName("pernyataanA")
    @Expose
    private String pernyataanA;
    @SerializedName("pernyataanB")
    @Expose
    private String pernyataanB;
    @SerializedName("kondisiA")
    @Expose
    private String kondisiA;
    @SerializedName("kondisiB")
    @Expose
    private String kondisiB;

    public String getIdSoal() {
        return idSoal;
    }

    public void setIdSoal(String idSoal) {
        this.idSoal = idSoal;
    }

    public String getPernyataanA() {
        return pernyataanA;
    }

    public void setPernyataanA(String pernyataanA) {
        this.pernyataanA = pernyataanA;
    }

    public String getPernyataanB() {
        return pernyataanB;
    }

    public void setPernyataanB(String pernyataanB) {
        this.pernyataanB = pernyataanB;
    }

    public String getKondisiA() {
        return kondisiA;
    }

    public void setKondisiA(String kondisiA) {
        this.kondisiA = kondisiA;
    }

    public String getKondisiB() {
        return kondisiB;
    }

    public void setKondisiB(String kondisiB) {
        this.kondisiB = kondisiB;
    }

}
