package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 18/01/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PsychologistApprove implements Parcelable {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("wa")
    @Expose
    private Object wa;
    @SerializedName("id_line")
    @Expose
    private Object idLine;
    @SerializedName("bio")
    @Expose
    private Object bio;
    @SerializedName("jns_kel")
    @Expose
    private Object jnsKel;
    @SerializedName("alamat")
    @Expose
    private Object alamat;
    @SerializedName("foto")
    @Expose
    private Object foto;
    @SerializedName("status_kabim")
    @Expose
    private String statusKabim;
    @SerializedName("approval_kabim")
    @Expose
    private String approvalKabim;
    @SerializedName("status_psikolog")
    @Expose
    private String statusPsikolog;
    @SerializedName("approval_psikolog")
    @Expose
    private String approvalPsikolog;
    @SerializedName("tarif")
    @Expose
    private String tarif;

    protected PsychologistApprove(Parcel in) {
        username = in.readString();
        email = in.readString();
        id = in.readString();
        nama = in.readString();
        statusKabim = in.readString();
        approvalKabim = in.readString();
        statusPsikolog = in.readString();
        approvalPsikolog = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(id);
        dest.writeString(nama);
        dest.writeString(statusKabim);
        dest.writeString(approvalKabim);
        dest.writeString(statusPsikolog);
        dest.writeString(approvalPsikolog);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PsychologistApprove> CREATOR = new Creator<PsychologistApprove>() {
        @Override
        public PsychologistApprove createFromParcel(Parcel in) {
            return new PsychologistApprove(in);
        }

        @Override
        public PsychologistApprove[] newArray(int size) {
            return new PsychologistApprove[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Object getWa() {
        return wa;
    }

    public void setWa(Object wa) {
        this.wa = wa;
    }

    public Object getIdLine() {
        return idLine;
    }

    public void setIdLine(Object idLine) {
        this.idLine = idLine;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getJnsKel() {
        return jnsKel;
    }

    public void setJnsKel(Object jnsKel) {
        this.jnsKel = jnsKel;
    }

    public Object getAlamat() {
        return alamat;
    }

    public void setAlamat(Object alamat) {
        this.alamat = alamat;
    }

    public Object getFoto() {
        return foto;
    }

    public void setFoto(Object foto) {
        this.foto = foto;
    }

    public String getStatusKabim() {
        return statusKabim;
    }

    public void setStatusKabim(String statusKabim) {
        this.statusKabim = statusKabim;
    }

    public String getApprovalKabim() {
        return approvalKabim;
    }

    public void setApprovalKabim(String approvalKabim) {
        this.approvalKabim = approvalKabim;
    }

    public String getStatusPsikolog() {
        return statusPsikolog;
    }

    public void setStatusPsikolog(String statusPsikolog) {
        this.statusPsikolog = statusPsikolog;
    }

    public String getApprovalPsikolog() {
        return approvalPsikolog;
    }

    public void setApprovalPsikolog(String approvalPsikolog) {
        this.approvalPsikolog = approvalPsikolog;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }
}