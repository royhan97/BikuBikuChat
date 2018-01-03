package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 03/01/2018.
 */

public class User {
    private String id;
    private String username;
    private String email;
    private String token;
    private String kodeSaku;
    private String statusAkun;
    private String nama;
    private String wa;
    private String idLine;
    private String bio;
    private String jnsKel;
    private String alamat;
    private String foto;
    private String statusKabim;
    private String approvalKabim;
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

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getKodeSaku() {
        return kodeSaku;
    }

    public String getStatusAkun() {
        return statusAkun;
    }

    public String getNama() {
        return nama;
    }

    public String getWa() {
        return wa;
    }

    public String getIdLine() {
        return idLine;
    }

    public String getBio() {
        return bio;
    }

    public String getJnsKel() {
        return jnsKel;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getFoto() {
        return foto;
    }

    public String getStatusKabim() {
        return statusKabim;
    }

    public String getApprovalKabim() {
        return approvalKabim;
    }

    public String getIdHistoryUser() {
        return idHistoryUser;
    }
}
