package com.example.adhit.bikubiku.ui.mylibrary;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.TabFragmentPagerAdapter;
import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.presenter.MyLibraryPresenter;
import com.example.adhit.bikubiku.presenter.ProfilPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLibraryFragment extends Fragment implements MyLibraryView {

    private ViewPager pager;
    private TabLayout tabs;
    private TabFragmentPagerAdapter adapter;
    private MyLibraryPresenter myLibraryPresenter;
    public MyLibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((HomeActivity)getActivity()).findViewById(R.id.navigation).setVisibility(View.GONE);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_my_library, container, false);
        pager = (ViewPager)view.findViewById(R.id.pager);
        tabs = (TabLayout)view.findViewById(R.id.tabs);
        initView();
        return view;
    }

    public void initView(){
        adapter = new TabFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        tabs.setTabTextColors(getResources().getColor(R.color.color_black),
                getResources().getColor(android.R.color.background_dark));
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        myLibraryPresenter = new MyLibraryPresenter(this);
        myLibraryPresenter.showFragmentList();
    }

    @Override
    public void showData(ArrayList<Fraggment> fraggmentArrayList) {
        adapter.setData(fraggmentArrayList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((HomeActivity)getActivity()).findViewById(R.id.navigation).setVisibility(View.VISIBLE);
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

}
