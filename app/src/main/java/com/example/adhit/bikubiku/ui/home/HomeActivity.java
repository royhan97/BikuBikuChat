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
import com.example.adhit.bikubiku.service.RequestKabimService;
import com.example.adhit.bikubiku.ui.home.akun.AkunFragment;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.ui.listpsychologist.ListPsychologistFragment;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private static final int GPS_REQUEST_CODE = 1000;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private BottomNavigationView navigation;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int errorCheck = api.isGooglePlayServicesAvailable(this);
        if(errorCheck == ConnectionResult.SUCCESS) {
            ShowAlert.showToast(this,"Google Service Available");
        } else if(api.isUserResolvableError(errorCheck)) {
            //GPS_REQUEST_CODE = 1000, and is used in onActivityResult
            api.showErrorDialogFragment(this, errorCheck, GPS_REQUEST_CODE);
            //stop our activity initialization code
            return;
        } else {
            //GPS not available, user cannot resolve this error
            //todo: somehow inform user or fallback to different method
            //stop our activity initialization code
            ShowAlert.showToast(this, "Google Play Service not available");
            return;
        }

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

        if (SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
            PeriodicTask periodic = new PeriodicTask.Builder()
                    .setService(RequestKabimService.class)
                    .setPeriod(10)
                    .setFlex(5)
                    .setTag(RequestKabimService.TAG_LIST_REQUEST)
                    .setPersisted(false)
                    .setRequiredNetwork(com.google.android.gms.gcm.Task.NETWORK_STATE_ANY)
                    .setRequiresCharging(false)
                    .setUpdateCurrent(true)
                    .build();

            GcmNetworkManager.getInstance(this).schedule(periodic);
        }
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
