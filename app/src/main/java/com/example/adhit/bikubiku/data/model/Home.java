package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 03/01/2018.
 */

public class Home {

    private int gambarMenu;
    private String namaMenu;

    public Home(int gambarMenu, String namaMenu) {
        this.gambarMenu = gambarMenu;
        this.namaMenu = namaMenu;
    }

    public int getGambarMenu() {
        return gambarMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }
}
