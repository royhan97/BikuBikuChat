package com.example.adhit.bikubiku.ui.profil;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.TabFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    private ViewPager pager;
    private TabLayout tabs;
    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        pager = (ViewPager)view.findViewById(R.id.pager);
        tabs = (TabLayout)view.findViewById(R.id.tabs);
        initView();
        return view;
    }

    public void initView(){

        //set object adapter kedalam ViewPager

        pager.setAdapter(new TabFragmentPagerAdapter(getFragmentManager()));

        //Manimpilasi sedikit untuk set TextColor pada Tab
        tabs.setTabTextColors(getResources().getColor(R.color.color_black),
                getResources().getColor(android.R.color.background_dark));
        //set tab ke ViewPager
        tabs.setupWithViewPager(pager);

        //konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            tabs.setLayoutMode(TabLayout.MODE_SCROLLABLE);
        }
    }

}
