package com.example.adhit.bikubiku.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by adhit on 03/01/2018.
 */

public class User {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("kode_saku")
    @Expose
    private String kodeSaku;
    @SerializedName("status_akun")
    @Expose
    private String statusAkun;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("wa")
    @Expose
    private String wa;
    @SerializedName("id_line")
    @Expose
    private String idLine;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("jns_kel")
    @Expose
    private String jnsKel;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("foto")
    @Expose
    private String foto;
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
    @SerializedName("id_history_user")
    @Expose
    private String idHistoryUser;


    public User(String id, String username, String email, String token, String kodeSaku, String statusAkun, String nama, String wa, String idLine, String bio, String jnsKel, String alamat, String foto, String statusKabim, String approvalKabim, String idHistoryUser) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
        this.kodeSaku = kodeSaku;
        this.statusAkun = statusAkun;
        this.nama = nama;
        this.wa = wa;
        this.idLine = idLine;
        this.bio = bio;
        this.jnsKel = jnsKel;
        this.alamat = alamat;
        this.foto = foto;
        this.statusKabim = statusKabim;
        this.approvalKabim = approvalKabim;
        this.idHistoryUser = idHistoryUser;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKodeSaku() {
        return kodeSaku;
    }

    public void setKodeSaku(String kodeSaku) {
        this.kodeSaku = kodeSaku;
    }

    public String getStatusAkun() {
        return statusAkun;
    }

    public void setStatusAkun(String statusAkun) {
        this.statusAkun = statusAkun;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWa() {
        return wa;
    }

    public void setWa(String wa) {
        this.wa = wa;
    }

    public String getIdLine() {
        return idLine;
    }

    public void setIdLine(String idLine) {
        this.idLine = idLine;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getJnsKel() {
        return jnsKel;
    }

    public void setJnsKel(String jnsKel) {
        this.jnsKel = jnsKel;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
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

    public String getIdHistoryUser() {
        return idHistoryUser;
    }

    public void setIdHistoryUser(String idHistoryUser) {
        this.idHistoryUser = idHistoryUser;
    }
}
