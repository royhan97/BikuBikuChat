package com.example.adhit.bikubiku.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SQLLite;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.ui.home.akun.AkunFragment;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.util.ShowAlert;

public class HomeActivity extends AppCompatActivity {
     int i,j;
     private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    public  void initView(){
        appBarLayout = findViewById(R.id.appbarlayout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        i=0;j=0;
        getSupportFragmentManager().beginTransaction().
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                replace(R.id.frame_container,
                        new HomeFragment(),
                        HomeFragment.class.getSimpleName()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                        appBarLayout.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.frame_container,
                                        new HomeFragment(),
                                        HomeFragment.class.getSimpleName()).commit();
                        return true;


                case R.id.navigation_account:

                        appBarLayout.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.frame_container,
                                        new AkunFragment(),
                                        AkunFragment.class.getSimpleName()).commit();

                    return true;
            }
            return false;
        }
    };

}
