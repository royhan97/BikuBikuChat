package com.example.adhit.bikubiku.ui.detailpsychologist;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.util.ShowAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPsychologistFragment extends Fragment {


    public DetailPsychologistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_psychologist, container, false);
        ShowAlert.showToast(getActivity(),Integer.toString(getFragmentManager().getBackStackEntryCount()) );
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

}
