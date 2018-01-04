package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SaveUserToken;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.ui.home.akun.AkunView;
import com.example.adhit.bikubiku.ui.profil.ProfilFragment;
import com.example.adhit.bikubiku.ui.profil.ProfilView;

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
        fraggmentArrayList.add(new Fraggment(new ProfilFragment(), "Profil"));
        fraggmentArrayList.add(new Fraggment(new ProfilFragment(), "Alamat"));
        fraggmentArrayList.add(new Fraggment(new ProfilFragment(), "Social/ Messenger"));
        fraggmentArrayList.add(new Fraggment(new ProfilFragment(), "Ubah Password"));

        profilView.showData(fraggmentArrayList);

    }

}
