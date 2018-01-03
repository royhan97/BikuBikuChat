package com.example.adhit.bikubiku.ui.login;

/**
 * Created by adhit on 03/01/2018.
 */

public interface LoginView {
    void showMessage(String string);

    void moveActivity(boolean b);

    void showMessageSnackbar(String message);
}
