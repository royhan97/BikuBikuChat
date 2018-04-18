package com.example.adhit.bikubiku.ui.detailakun.personalia;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.presenter.PersonaliaPresenter;
import com.example.adhit.bikubiku.ui.tesminat.TesMinatActivity;
import com.example.adhit.bikubiku.util.ShowAlert;

public class PersonaliaActivity extends AppCompatActivity implements PersonaliaView {

    private PersonaliaPresenter personaliaPresenter;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalia);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Personalia");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initPresenter();
    }

    private void initPresenter(){
        if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
            ShowAlert.closeProgresDialog();
        }
        ShowAlert.showProgresDialog(PersonaliaActivity.this);
        personaliaPresenter = new PersonaliaPresenter(this);
        personaliaPresenter.showPersonalia();
    }

    @Override
    public void onFailedShowPersonalia(String message) {
        if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
            ShowAlert.closeProgresDialog();
        }
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });

        alert = builder.create();
        alert.show();
    }

    @Override
    public void onSuccessShowPersonalia(String message) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
