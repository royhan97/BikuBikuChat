package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.ui.personalia.PersonaliaView;
import com.example.adhit.bikubiku.ui.personalia.persnolatity.PersonalityFragment;
import com.example.adhit.bikubiku.ui.personalia.suggestion.SuggestionFragment;


import java.util.ArrayList;

/**
 * Created by adhit on 04/01/2018.
 */

public class PersonaliaPresenter {
    private PersonaliaView personaliaView;
    public PersonaliaPresenter(PersonaliaView personaliaView){
        this.personaliaView = personaliaView;
    }

    public void showFragmentList(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new PersonalityFragment(), "Kepribadian"));
        fraggmentArrayList.add(new Fraggment(new SuggestionFragment(), "Saran"));

        personaliaView.showData(fraggmentArrayList);

    }
}
