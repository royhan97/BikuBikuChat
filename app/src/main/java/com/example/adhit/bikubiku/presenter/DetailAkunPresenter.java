package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.ui.detailakun.DetailAkunView;
import com.example.adhit.bikubiku.ui.detailakun.mylibrary.shared.SharedFragment;
import com.example.adhit.bikubiku.ui.detailakun.mylibrary.studied.StudiedFragment;
import com.example.adhit.bikubiku.ui.detailakun.panelkabim.history.HistoryFragment;
import com.example.adhit.bikubiku.ui.detailakun.panelkabim.kabimsettings.KabimSettingsFragment;
import com.example.adhit.bikubiku.ui.detailakun.personalia.persnolatity.PersonalityFragment;
import com.example.adhit.bikubiku.ui.detailakun.personalia.suggestion.SuggestionFragment;
import com.example.adhit.bikubiku.ui.detailakun.profil.accountsettings.AccountSettingsFragment;
import com.example.adhit.bikubiku.ui.detailakun.profil.address.AddressFragment;
import com.example.adhit.bikubiku.ui.detailakun.profil.changepassword.ChangePasswordFragment;

import java.util.ArrayList;

/**
 * Created by adhit on 05/01/2018.
 */

public class DetailAkunPresenter {

    private DetailAkunView detailAkunView;
    public DetailAkunPresenter(DetailAkunView detailAkunView){
        this.detailAkunView = detailAkunView;
    }

    public void showFragmentListMyLibrary(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new StudiedFragment(), "Dipelajari"));
        fraggmentArrayList.add(new Fraggment(new SharedFragment(), "Dibagikan"));
        detailAkunView.showData(fraggmentArrayList);
    }

    public void showFragmentListPanelKabim(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new KabimSettingsFragment(), "Pengaturan"));
        fraggmentArrayList.add(new Fraggment(new HistoryFragment(), "History"));
        detailAkunView.showData(fraggmentArrayList);
    }

    public void showFragmentListPersonalia(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new PersonalityFragment(), "Kepribadian"));
        fraggmentArrayList.add(new Fraggment(new SuggestionFragment(), "Saran"));
        detailAkunView.showData(fraggmentArrayList);
    }

    public void showFragmentListProfil(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new AccountSettingsFragment(), "Profil"));
        fraggmentArrayList.add(new Fraggment(new AddressFragment(), "Alamat"));
        fraggmentArrayList.add(new Fraggment(new ChangePasswordFragment(), "Password"));
        detailAkunView.showData(fraggmentArrayList);
    }

}
