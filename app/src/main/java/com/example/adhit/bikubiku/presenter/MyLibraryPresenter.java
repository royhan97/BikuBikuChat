package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.ui.mylibrary.MyLibraryView;
import com.example.adhit.bikubiku.ui.mylibrary.shared.SharedFragment;
import com.example.adhit.bikubiku.ui.mylibrary.studied.StudiedFragment;
import com.example.adhit.bikubiku.ui.personalia.PersonaliaView;
import com.example.adhit.bikubiku.ui.personalia.persnolatity.PersonalityFragment;
import com.example.adhit.bikubiku.ui.personalia.suggestion.SuggestionFragment;

import java.util.ArrayList;

/**
 * Created by adhit on 04/01/2018.
 */

public class MyLibraryPresenter {
    private MyLibraryView myLibraryView;
    public MyLibraryPresenter(MyLibraryView myLibraryView){
        this.myLibraryView = myLibraryView;
    }

    public void showFragmentList(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new StudiedFragment(), "Dipelajari"));
        fraggmentArrayList.add(new Fraggment(new SharedFragment(), "Dibagikan"));

        myLibraryView.showData(fraggmentArrayList);

    }
}
