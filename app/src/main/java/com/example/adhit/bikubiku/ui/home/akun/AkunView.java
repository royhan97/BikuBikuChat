package com.example.adhit.bikubiku.ui.home.akun;

import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.model.User;

import java.util.ArrayList;

/**
 * Created by adhit on 04/01/2018.
 */

public interface AkunView {
    void showData(ArrayList<Home> akunArrayList);

    void showUserData(User user);
}
