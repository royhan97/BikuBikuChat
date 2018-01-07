package com.example.adhit.bikubiku.ui.detailakun;

import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.TabFragmentPagerAdapter;
import com.example.adhit.bikubiku.data.model.Fraggment;
import com.example.adhit.bikubiku.presenter.DetailAkunPresenter;

import java.util.ArrayList;

public class DetailAkunActivity extends AppCompatActivity implements DetailAkunView {

    private ViewPager pager;
    private TabLayout tabs;
    private TabFragmentPagerAdapter adapter;
    private DetailAkunPresenter detailAkunPresenter;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_akun);
        coordinatorLayout = findViewById(R.id.coordinator);
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
        detailAkunPresenter = new DetailAkunPresenter(this);
        adapter = new TabFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        tabs.setTabTextColors(getResources().getColor(R.color.color_black),
                getResources().getColor(android.R.color.background_dark));
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        if(getIntent().getStringExtra("detail_akun").equals("Profil")){

            detailAkunPresenter.showFragmentListProfil();
        }
        if(getIntent().getStringExtra("detail_akun").equals("Personalia")){
            detailAkunPresenter.showFragmentListPersonalia();
        }
        if(getIntent().getStringExtra("detail_akun").equals("My Library")){
            detailAkunPresenter.showFragmentListMyLibrary();
        }
        if(getIntent().getStringExtra("detail_akun").equals("Panel Kabim")){
            detailAkunPresenter.showFragmentListPanelKabim();
        }

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
