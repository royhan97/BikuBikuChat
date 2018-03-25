package com.example.adhit.bikubiku.data.model;

/**
 * Created by ASUS on 3/24/2018.
 */

public class SakuBikuMenu {
    private int gambarMenu;
    private String namaMenu;

    public SakuBikuMenu(int gambarMenu, String namaMenu) {
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
