package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.ui.home.home.HomeView;

import java.util.ArrayList;

/**
 * Created by adhit on 03/01/2018.
 */

public class HomePresenter {

    private HomeView homeView;
    public HomePresenter(HomeView homeView){
        this.homeView = homeView;
    }

    public void showListHome(){
        ArrayList<Home> homeArrayList = new ArrayList<>();
        homeArrayList.add(new Home(R.drawable.logo, "Library"));
        homeArrayList.add(new Home(R.drawable.logo, "Ruang Belajar"));
        homeArrayList.add(new Home(R.drawable.logo, "Tes Minat"));
        homeArrayList.add(new Home(R.drawable.logo, "Artikel"));
        homeArrayList.add(new Home(R.drawable.logo, "Konsultasi Psikologi"));
        homeArrayList.add(new Home(R.drawable.logo, "History Konsultasi"));
        homeView.showData(homeArrayList);

    }
}
