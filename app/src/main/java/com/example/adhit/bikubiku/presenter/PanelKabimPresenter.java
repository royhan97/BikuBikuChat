package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.ui.panelkabim.PanelKabimView;
import com.example.adhit.bikubiku.ui.panelkabim.history.HistoryFragment;
import com.example.adhit.bikubiku.ui.panelkabim.kabimsettings.KabimSettingsFragment;
import com.example.adhit.bikubiku.ui.personalia.PersonaliaView;
import com.example.adhit.bikubiku.ui.personalia.persnolatity.PersonalityFragment;
import com.example.adhit.bikubiku.ui.personalia.suggestion.SuggestionFragment;

import java.util.ArrayList;

/**
 * Created by adhit on 04/01/2018.
 */

public class PanelKabimPresenter {
    private PanelKabimView panelKabimView;
    public PanelKabimPresenter(PanelKabimView panelKabimView){
        this.panelKabimView = panelKabimView;
    }

    public void showFragmentList(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new KabimSettingsFragment(), "Pengaturan"));
        fraggmentArrayList.add(new Fraggment(new HistoryFragment(), "History"));

        panelKabimView.showData(fraggmentArrayList);

    }
}
