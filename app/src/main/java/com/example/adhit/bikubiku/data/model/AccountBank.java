package com.example.adhit.bikubiku.data.model;

/**
 * Created by ASUS on 3/25/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountBank {

    @SerializedName("kode_saku")
    @Expose
    private String kodeSaku;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("rekening")
    @Expose
    private String rekening;
    @SerializedName("atas_nama")
    @Expose
    private String atasNama;
    @SerializedName("keterangan")
    @Expose
    private Object keterangan;

    public String getKodeSaku() {
        return kodeSaku;
    }

    public void setKodeSaku(String kodeSaku) {
        this.kodeSaku = kodeSaku;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getAtasNama() {
        return atasNama;
    }

    public void setAtasNama(String atasNama) {
        this.atasNama = atasNama;
    }

    public Object getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(Object keterangan) {
        this.keterangan = keterangan;
    }

}