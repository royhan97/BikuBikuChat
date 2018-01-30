package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 09/01/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRoomPsychologyHistory {

    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("id_biquers")
    @Expose
    private String idBiquers;
    @SerializedName("id_kabim")
    @Expose
    private String idKabim;
    @SerializedName("layanan")
    @Expose
    private String layanan;
    @SerializedName("kode_mapel")
    @Expose
    private Object kodeMapel;
    @SerializedName("kode_jenjang")
    @Expose
    private Object kodeJenjang;
    @SerializedName("lama")
    @Expose
    private String lama;
    @SerializedName("penjelasan")
    @Expose
    private String penjelasan;
    @SerializedName("nominal_bayar")
    @Expose
    private String nominalBayar;
    @SerializedName("kupon")
    @Expose
    private Object kupon;
    @SerializedName("id_room")
    @Expose
    private String idRoom;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("status_trx")
    @Expose
    private String statusTrx;
    @SerializedName("nama_mapel")
    @Expose
    private Object namaMapel;
    @SerializedName("keterangan")
    @Expose
    private Object keterangan;
    @SerializedName("aktif")
    @Expose
    private Object aktif;
    @SerializedName("nama_jenjang")
    @Expose
    private Object namaJenjang;
    @SerializedName("nama_biquers")
    @Expose
    private String namaBiquers;
    @SerializedName("foto_biquers")
    @Expose
    private Object fotoBiquers;
    @SerializedName("nama_kabim")
    @Expose
    private String namaKabim;
    @SerializedName("foto_kabim")
    @Expose
    private Object fotoKabim;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getIdBiquers() {
        return idBiquers;
    }

    public void setIdBiquers(String idBiquers) {
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

    public Object getKodeMapel() {
        return kodeMapel;
    }

    public void setKodeMapel(Object kodeMapel) {
        this.kodeMapel = kodeMapel;
    }

    public Object getKodeJenjang() {
        return kodeJenjang;
    }

    public void setKodeJenjang(Object kodeJenjang) {
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

    public String getNominalBayar() {
        return nominalBayar;
    }

    public void setNominalBayar(String nominalBayar) {
        this.nominalBayar = nominalBayar;
    }

    public Object getKupon() {
        return kupon;
    }

    public void setKupon(Object kupon) {
        this.kupon = kupon;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatusTrx() {
        return statusTrx;
    }

    public void setStatusTrx(String statusTrx) {
        this.statusTrx = statusTrx;
    }

    public Object getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(Object namaMapel) {
        this.namaMapel = namaMapel;
    }

    public Object getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(Object keterangan) {
        this.keterangan = keterangan;
    }

    public Object getAktif() {
        return aktif;
    }

    public void setAktif(Object aktif) {
        this.aktif = aktif;
    }

    public Object getNamaJenjang() {
        return namaJenjang;
    }

    public void setNamaJenjang(Object namaJenjang) {
        this.namaJenjang = namaJenjang;
    }

    public String getNamaBiquers() {
        return namaBiquers;
    }

    public void setNamaBiquers(String namaBiquers) {
        this.namaBiquers = namaBiquers;
    }

    public Object getFotoBiquers() {
        return fotoBiquers;
    }

    public void setFotoBiquers(Object fotoBiquers) {
        this.fotoBiquers = fotoBiquers;
    }

    public String getNamaKabim() {
        return namaKabim;
    }

    public void setNamaKabim(String namaKabim) {
        this.namaKabim = namaKabim;
    }

    public Object getFotoKabim() {
        return fotoKabim;
    }

    public void setFotoKabim(Object fotoKabim) {
        this.fotoKabim = fotoKabim;
    }

}