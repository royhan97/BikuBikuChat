package com.example.adhit.bikubiku.adapter;



import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.ui.profil.PengaturanAkunFragment;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    /**
     * Contains all the fragments.
     */
    private List<Fraggment> fragments = new ArrayList<>();

    /**
     * Creates a new PagerAdapter instance.
     *
     * @param fragmentManager The FragmentManager.
     */
    public TabFragmentPagerAdapter(FragmentManager fragmentManager ) {
        super(fragmentManager);


    }

    public void setData(List<Fraggment> fraggments){
        this.fragments = fraggments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFraggment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }


}