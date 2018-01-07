package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 07/01/2018.
 */

public class Psychologist {
    private int id;
    private String nama;

    public Psychologist(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }
}
