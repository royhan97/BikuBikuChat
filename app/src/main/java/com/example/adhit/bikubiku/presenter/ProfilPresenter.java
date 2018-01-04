package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.ui.profil.ProfilView;
import com.example.adhit.bikubiku.ui.profil.accountsettings.AccountSettingsFragment;
import com.example.adhit.bikubiku.ui.profil.address.AddressFragment;
import com.example.adhit.bikubiku.ui.profil.passwordchange.PasswordChangeFragment;
import com.example.adhit.bikubiku.ui.profil.socialmedia.SocialMediaFragment;

import java.util.ArrayList;

/**
 * Created by adhit on 04/01/2018.
 */

public class ProfilPresenter {
    private ProfilView profilView;
    public ProfilPresenter(ProfilView profilView){
        this.profilView = profilView;
    }

    public void showFragmentList(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new AccountSettingsFragment(), "Profil"));
        fraggmentArrayList.add(new Fraggment(new AddressFragment(), "Alamat"));
        fraggmentArrayList.add(new Fraggment(new SocialMediaFragment(), "Social/ Messenger"));
        fraggmentArrayList.add(new Fraggment(new PasswordChangeFragment(), "Ubah Password"));

        profilView.showData(fraggmentArrayList);

    }

}
