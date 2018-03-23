package com.example.adhit.bikubiku.ui.home.home;

import com.example.adhit.bikubiku.data.model.Home;

import java.util.ArrayList;

/**
 * Created by adhit on 03/01/2018.
 */

public interface HomeView {
    void showData(ArrayList<Home> homeArrayList);
    

    void showError(String s);

    void onFailedShowSaldo(String error);

    void onSuccessShowSaldo(String s);

    void onSuccessShowName(String s);
}
