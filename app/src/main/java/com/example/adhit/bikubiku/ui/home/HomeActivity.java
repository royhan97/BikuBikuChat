package com.example.adhit.bikubiku.ui.home;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.service.RequestKabimService;
import com.example.adhit.bikubiku.ui.home.akun.AkunFragment;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.ui.listpsychologist.ListPsychologistFragment;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements ChattingPsychologyView {

    private static final int GPS_REQUEST_CODE = 1000;
    private ChattingPsychologyPresenter chattingPsychologyPresenter;
    private Toolbar toolbar;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        chattingPsychologyPresenter = new ChattingPsychologyPresenter(this);
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int errorCheck = api.isGooglePlayServicesAvailable(this);
        if(errorCheck == ConnectionResult.SUCCESS) {

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
        if(getIntent().getIntExtra("id_room",0)> 0){
            chattingPsychologyPresenter.openRoomChatById(getIntent().getIntExtra("id_room",0));
        }


    }

    public  void initView(){
        toolbar = findViewById(R.id.toolbar);
        imgLogo = findViewById(R.id.img_logo);
        setSupportActionBar(toolbar);
        setTitle("");
        getFragmentManager().beginTransaction().
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                replace(R.id.frame_container,
                        new HomeFragment(),
                        HomeFragment.class.getSimpleName()).commit();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(getFragmentManager().getBackStackEntryCount()==0){
            imgLogo.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void canCreateRoom(boolean b) {

    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChattingPsychologyActivity.generateIntent(getApplicationContext(), qiscusChatRoom, false);
        startActivity(intent);
    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void onFailure(String failed) {

    }

    @Override
    public void onFinishTransaction() {

    }



}
