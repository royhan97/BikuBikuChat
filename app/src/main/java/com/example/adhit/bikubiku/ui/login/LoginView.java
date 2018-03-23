package com.example.adhit.bikubiku.ui.login;

/**
 * Created by adhit on 03/01/2018.
 */

public interface LoginView {

    void gotoHome();

    void onFailedLogin(String message);

    void onSuccessLogin(String s);
}
