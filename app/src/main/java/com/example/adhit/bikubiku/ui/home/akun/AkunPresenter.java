package com.example.adhit.bikubiku.ui.home.akun;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.ui.home.home.HomeView;

import java.util.ArrayList;

/**
 * Created by adhit on 04/01/2018.
 */

public class AkunPresenter {
    private AkunView akunView;
    public AkunPresenter(AkunView akunView){
        this.akunView = akunView;
    }

    public void showListAkunMenu(){
        ArrayList<Home> akunArrayList = new ArrayList<>();
        akunArrayList.add(new Home(R.drawable.logo, "Profil"));
        akunArrayList.add(new Home(R.drawable.logo, "Personalia"));
        akunArrayList.add(new Home(R.drawable.logo, "My Library"));
        akunArrayList.add(new Home(R.drawable.logo, "Pencapaian"));
        akunArrayList.add(new Home(R.drawable.logo, "Panel Kabim"));
        akunArrayList.add(new Home(R.drawable.logo, "Saku Biku"));
        akunArrayList.add(new Home(R.drawable.logo, "Log Out"));
        akunView.showData(akunArrayList);

    }
}
