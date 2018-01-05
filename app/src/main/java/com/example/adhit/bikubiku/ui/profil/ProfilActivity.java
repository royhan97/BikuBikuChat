package com.example.adhit.bikubiku.ui.profil;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.TabFragmentPagerAdapter;
import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.presenter.ProfilPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity implements ProfilView {

    private ViewPager pager;
    private TabLayout tabs;
    private TabFragmentPagerAdapter adapter;
    private ProfilPresenter profilPresenter;
    private Toolbar toolbar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
    }

    public void initView(){


        adapter = new TabFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        tabs.setTabTextColors(getResources().getColor(R.color.color_black),
                getResources().getColor(android.R.color.background_dark));
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        profilPresenter = new ProfilPresenter(this);
        profilPresenter.showFragmentList();
    }

    @Override
    public void showData(ArrayList<Fraggment> fraggmentArrayList) {
        adapter.setData(fraggmentArrayList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
