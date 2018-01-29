package com.example.adhit.bikubiku.ui.home;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.ui.home.akun.AkunFragment;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.listpsychologist.ListPsychologistFragment;
import com.example.adhit.bikubiku.util.ShowAlert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
     private Toolbar toolbar;
     private BottomNavigationView navigation;
     private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    public  void initView(){
        appBarLayout = findViewById(R.id.appbarlayout);
        toolbar = findViewById(R.id.toolbar);
        imgLogo = findViewById(R.id.img_logo);
        setSupportActionBar(toolbar);
        setTitle("");
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getFragmentManager().beginTransaction().
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                replace(R.id.frame_container,
                        new HomeFragment(),
                        HomeFragment.class.getSimpleName()).commit();

//        if(getIntent().getStringExtra("fragment").equals("History Konsultasi")){
//            getFragmentManager().beginTransaction().
//                    replace(R.id.frame_container,
//                            new ListPsychologistFragment(),
//                            ListPsychologistFragment.class.getSimpleName())
//                    .addToBackStack(ListPsychologistFragment.class.getSimpleName())
//                    .commit();
//        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getFragmentManager().beginTransaction().
                            replace(R.id.frame_container,
                                    new HomeFragment(),
                                    HomeFragment.class.getSimpleName())
                            .commit();
                    return true;


                case R.id.navigation_account:
                      getFragmentManager().beginTransaction().
                            replace(R.id.frame_container,
                                    new AkunFragment(),
                                    AkunFragment.class.getSimpleName())

                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

       if(getFragmentManager().getBackStackEntryCount()==0){
            navigation.setVisibility(View.VISIBLE);
            imgLogo.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

    }


}
