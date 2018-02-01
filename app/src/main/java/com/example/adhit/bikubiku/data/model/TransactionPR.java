package com.example.adhit.bikubiku.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roy on 1/24/2018.
 */

public class TransactionPR {

    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("id_biquers")
    @Expose
    private Integer idBiquers;
    @SerializedName("id_kabim")
    @Expose
    private String idKabim;
    @SerializedName("layanan")
    @Expose
    private String layanan;
    @SerializedName("kode_mapel")
    @Expose
    private String kodeMapel;
    @SerializedName("kode_jenjang")
    @Expose
    private String kodeJenjang;
    @SerializedName("lama")
    @Expose
    private String lama;
    @SerializedName("penjelasan")
    @Expose
    private String penjelasan;
    @SerializedName("nominal_bayar")
    @Expose
    private Integer nominalBayar;
    @SerializedName("kupon")
    @Expose
    private Object kupon;
    @SerializedName("createDate")
    @Expose
    private Object createDate;
    @SerializedName("status_trx")
    @Expose
    private Integer statusTrx;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Integer getIdBiquers() {
        return idBiquers;
    }

    public void setIdBiquers(Integer idBiquers) {
        this.idBiquers = idBiquers;
    }

    public String getIdKabim() {
        return idKabim;
    }

    public void setIdKabim(String idKabim) {
        this.idKabim = idKabim;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public String getKodeMapel() {
        return kodeMapel;
    }

    public void setKodeMapel(String kodeMapel) {
        this.kodeMapel = kodeMapel;
    }

    public String getKodeJenjang() {
        return kodeJenjang;
    }

    public void setKodeJenjang(String kodeJenjang) {
        this.kodeJenjang = kodeJenjang;
    }

    public String getLama() {
        return lama;
    }

    public void setLama(String lama) {
        this.lama = lama;
    }

    public String getPenjelasan() {
        return penjelasan;
    }

    public void setPenjelasan(String penjelasan) {
        this.penjelasan = penjelasan;
    }

    public Integer getNominalBayar() {
        return nominalBayar;
    }

    public void setNominalBayar(Integer nominalBayar) {
        this.nominalBayar = nominalBayar;
    }

    public Object getKupon() {
        return kupon;
    }

    public void setKupon(Object kupon) {
        this.kupon = kupon;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Integer getStatusTrx() {
        return statusTrx;
    }

    public void setStatusTrx(Integer statusTrx) {
        this.statusTrx = statusTrx;
    }
}
