package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 18/01/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PsychologistUnapprove {

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

}