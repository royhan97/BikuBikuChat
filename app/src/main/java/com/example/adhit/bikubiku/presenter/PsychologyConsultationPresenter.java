package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.Psychologist;
import com.example.adhit.bikubiku.ui.psychologyconsultation.PsychologyConsultationView;

import java.util.ArrayList;

/**
 * Created by adhit on 07/01/2018.
 */

public class PsychologyConsultationPresenter {
    private PsychologyConsultationView psychologyConsultationView;
    public PsychologyConsultationPresenter(PsychologyConsultationView psychologyConsultationView){
        this.psychologyConsultationView = psychologyConsultationView;
    }

    public void psychologyList(){
        ArrayList<Psychologist> psychologistArrayList = new ArrayList<>();
        psychologistArrayList.add(new Psychologist(23, "Cahyo Adhi", "20.000"));
        psychologyConsultationView.showData(psychologistArrayList);
    }

}
