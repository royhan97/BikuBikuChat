package com.example.adhit.bikubiku.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roy on 1/22/2018.
 */

public class RequestToKabim implements Parcelable{

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
    private String namaMapel;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("aktif")
    @Expose
    private String aktif;
    @SerializedName("nama_jenjang")
    @Expose
    private String namaJenjang;
    @SerializedName("nama_biquers")
    @Expose
    private String namaBiquers;
    @SerializedName("foto_biquers")
    @Expose
    private String fotoBiquers;
    @SerializedName("nama_kabim")
    @Expose
    private String namaKabim;
    @SerializedName("foto_kabim")
    @Expose
    private String fotoKabim;

    protected RequestToKabim(Parcel in) {
        invoice = in.readString();
        idBiquers = in.readString();
        idKabim = in.readString();
        layanan = in.readString();
        kodeMapel = in.readString();
        kodeJenjang = in.readString();
        lama = in.readString();
        penjelasan = in.readString();
        nominalBayar = in.readString();
        idRoom = in.readString();
        createDate = in.readString();
        statusTrx = in.readString();
        namaMapel = in.readString();
        keterangan = in.readString();
        aktif = in.readString();
        namaJenjang = in.readString();
        namaBiquers = in.readString();
        namaKabim = in.readString();
    }

    public static final Creator<RequestToKabim> CREATOR = new Creator<RequestToKabim>() {
        @Override
        public RequestToKabim createFromParcel(Parcel in) {
            return new RequestToKabim(in);
        }

        @Override
        public RequestToKabim[] newArray(int size) {
            return new RequestToKabim[size];
        }
    };

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

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAktif() {
        return aktif;
    }

    public void setAktif(String aktif) {
        this.aktif = aktif;
    }

    public String getNamaJenjang() {
        return namaJenjang;
    }

    public void setNamaJenjang(String namaJenjang) {
        this.namaJenjang = namaJenjang;
    }

    public String getNamaBiquers() {
        return namaBiquers;
    }

    public void setNamaBiquers(String namaBiquers) {
        this.namaBiquers = namaBiquers;
    }

    public String getFotoBiquers() {
        return fotoBiquers;
    }

    public void setFotoBiquers(String fotoBiquers) {
        this.fotoBiquers = fotoBiquers;
    }

    public String getNamaKabim() {
        return namaKabim;
    }

    public void setNamaKabim(String namaKabim) {
        this.namaKabim = namaKabim;
    }

    public String getFotoKabim() {
        return fotoKabim;
    }

    public void setFotoKabim(String fotoKabim) {
        this.fotoKabim = fotoKabim;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(invoice);
        dest.writeString(idBiquers);
        dest.writeString(idKabim);
        dest.writeString(layanan);
        dest.writeString(kodeMapel);
        dest.writeString(kodeJenjang);
        dest.writeString(lama);
        dest.writeString(penjelasan);
        dest.writeString(nominalBayar);
        dest.writeString(idRoom);
        dest.writeString(createDate);
        dest.writeString(statusTrx);
        dest.writeString(namaMapel);
        dest.writeString(keterangan);
        dest.writeString(aktif);
        dest.writeString(namaJenjang);
        dest.writeString(namaBiquers);
        dest.writeString(namaKabim);
    }
}
