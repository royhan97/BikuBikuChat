package com.example.adhit.bikubiku.ui.detailakun.profil.accountsettings;

import com.example.adhit.bikubiku.data.model.User;

/**
 * Created by adhit on 06/01/2018.
 */

public interface AccountSettingsView {
    void getDataAccount(User user);

    void showMessage(String string);
}
