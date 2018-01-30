package com.example.adhit.bikubiku.ui.home.home;

import com.example.adhit.bikubiku.data.model.Home;

import java.util.ArrayList;

/**
 * Created by adhit on 03/01/2018.
 */

public interface HomeView {
    void showData(ArrayList<Home> homeArrayList);

    void showSaldo(String balance);

    void setSaldo(int jmlSaldo);

    void showError(String s);
}
