package com.example.adhit.bikubiku.ui.tesminat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.TesMinat;
import com.example.adhit.bikubiku.presenter.TesMinatPresenter;
import com.example.adhit.bikubiku.util.ShowAlert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TesMinatActivity extends AppCompatActivity implements TesMinatView {

    private TesMinatPresenter tesMinatPresenter;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private ArrayList<TesMinat> tesMinatArrayList;
    private Toolbar toolbar;
    private RelativeLayout rlTesMinat;
    private TextView tvQuestionNumber, tvQuestionA, tvQuestionB;
    private FloatingActionButton fabNextQuestion, fabPreviousQuestion;
    private boolean isTest;
    private int questionNumber;
    private Button btnSubmit;
    private HashMap<String, String> tesMinatHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes_minat);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Tes Minat");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvQuestionA = findViewById(R.id.tv_question_a);
        tvQuestionB = findViewById(R.id.tv_question_b);
        tvQuestionNumber = findViewById(R.id.tv_question_number);
        fabNextQuestion = findViewById(R.id.fab_next);
        fabPreviousQuestion = findViewById(R.id.fab_previous);
        rlTesMinat = findViewById(R.id.rl_tesminat);
        btnSubmit = findViewById(R.id.btn_submit);
        tesMinatArrayList = new ArrayList<>();
        tesMinatHashMap = new HashMap<>();
        isTest = false;
        questionNumber =1;
        initView();
        initPresenter();
    }

    private void initView(){
        btnSubmit.setVisibility(View.GONE);
        fabNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber++;
                tvQuestionA.setBackgroundResource(R.color.colorWhite);
                tvQuestionB.setBackgroundResource(R.color.colorWhite);
                int number = questionNumber-1;
                if(questionNumber == 60){
                    btnSubmit.setVisibility(View.VISIBLE);
                    fabNextQuestion.setVisibility(View.GONE);
                    tvQuestionA.setText(tesMinatArrayList.get(number).getPernyataanA());
                    tvQuestionB.setText(tesMinatArrayList.get(number).getPernyataanB());
                }else {
                    fabPreviousQuestion.setVisibility(View.VISIBLE);
                    tvQuestionA.setText(tesMinatArrayList.get(number).getPernyataanA());
                    tvQuestionB.setText(tesMinatArrayList.get(number).getPernyataanB());
                }
                tvQuestionNumber.setText(questionNumber+"/60");
                if(tesMinatHashMap.containsKey(questionNumber)){
                    String questionAnswer = tesMinatHashMap.get(questionNumber);
                    if(questionAnswer.equals(tesMinatArrayList.get(number).getKondisiA())){
                        tvQuestionA.setBackgroundResource(R.color.colorGreen400);
                    }else {
                        tvQuestionB.setBackgroundResource(R.color.colorGreen400);
                    }
                }

            }
        });
        fabPreviousQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                questionNumber--;
                tvQuestionA.setBackgroundResource(R.color.colorWhite);
                tvQuestionB.setBackgroundResource(R.color.colorWhite);
                if(questionNumber <60){
                    btnSubmit.setVisibility(View.GONE);
                }
                if(questionNumber == 1){
                    fabPreviousQuestion.setVisibility(View.GONE);
                    int number = questionNumber-1;
                    tvQuestionA.setText(tesMinatArrayList.get(number).getPernyataanA());
                    tvQuestionB.setText(tesMinatArrayList.get(number).getPernyataanB());
                }else {
                    fabNextQuestion.setVisibility(View.VISIBLE);
                    int number = questionNumber-1;
                    tvQuestionA.setText(tesMinatArrayList.get(number).getPernyataanA());
                    tvQuestionB.setText(tesMinatArrayList.get(number).getPernyataanB());

                }
                int number = questionNumber-1;
                tvQuestionNumber.setText(questionNumber+"/60");
                if(tesMinatHashMap.containsKey(questionNumber)){
                    String questionAnswer = tesMinatHashMap.get(questionNumber);
                    if(questionAnswer.equals(tesMinatArrayList.get(number).getKondisiA())){
                        tvQuestionA.setBackgroundResource(R.color.colorGreen400);
                    }else {
                        tvQuestionB.setBackgroundResource(R.color.colorGreen400);
                    }
                }
            }
        });
        tvQuestionA.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("ResourceAsColor")
           @Override
            public void onClick(View view) {
                int number = questionNumber-1;
                tvQuestionB.setBackgroundResource(R.color.colorWhite);
                tvQuestionA.setBackgroundResource(R.color.colorGreen400);
                tesMinatHashMap.put(Integer.toString(questionNumber), tesMinatArrayList.get(number).getKondisiA());

                //                if(questionNumber <= 60){
//
//                    int number = questionNumber-1;
//                    tvQuestionB.setBackgroundColor(R.color.colorWhite);
//                    tvQuestionA.setBackgroundColor(R.color.colorGreen400);
//                    tesMinatHashMap.put(questionNumber, tesMinatArrayList.get(number).getKondisiA());
//
//                    try {
//                        Thread.sleep(500);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }finally {
//                        if(questionNumber>=1 && questionNumber < 60){
//                            questionNumber++;
//                        }
//                        if(questionNumber == 60){
//                            fabNextQuestion.setVisibility(View.GONE);
//                        }
//                        number = questionNumber-1;
//                        tvQuestionA.setBackgroundColor(R.color.colorWhite);
//                        tvQuestionB.setBackgroundColor(R.color.colorWhite);
//                        tvQuestionA.setText(tesMinatArrayList.get(number).getPernyataanA());
//                        tvQuestionB.setText(tesMinatArrayList.get(number).getPernyataanB());
//                        tvQuestionNumber.setText(questionNumber+"/60");
//                        fabPreviousQuestion.setVisibility(View.VISIBLE);
//
//                    }
//                }



            }
        });

        tvQuestionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = questionNumber-1;
                tvQuestionA.setBackgroundResource(R.color.colorWhite);
                tvQuestionB.setBackgroundResource(R.color.colorGreen400);
                tesMinatHashMap.put(Integer.toString(questionNumber), tesMinatArrayList.get(number).getKondisiB());

//                if(questionNumber <= 60){
//
//                    int number = questionNumber-1;
//                    tvQuestionA.setBackgroundColor(R.color.colorWhite);
//                    tvQuestionB.setBackgroundColor(R.color.colorGreen400);
//                    tesMinatHashMap.put(questionNumber, tesMinatArrayList.get(number).getKondisiB());
//
//                    try {
//                        Thread.sleep(500);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }finally {
//                        if(questionNumber>=1 && questionNumber < 60){
//                            questionNumber++;
//                        }
//                        if(questionNumber == 60){
//                            fabNextQuestion.setVisibility(View.GONE);
//                        }
//                        number = questionNumber-1;
//                        tvQuestionA.setBackgroundColor(R.color.colorWhite);
//                        tvQuestionB.setBackgroundColor(R.color.colorWhite);
//                        tvQuestionA.setText(tesMinatArrayList.get(number).getPernyataanA());
//                        tvQuestionB.setText(tesMinatArrayList.get(number).getPernyataanB());
//                        tvQuestionNumber.setText(questionNumber+"/60");
//                        fabPreviousQuestion.setVisibility(View.VISIBLE);
//
//                    }
//                }


            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tesMinatHashMap.size() < 60){
                    ShowAlert.showToast(getApplicationContext(),"Anda belum menjawab semua soal tes minat");
                }else {
                    tesMinatPresenter.submitTesMinat(tesMinatHashMap);
                }
            }
        });
    }

    private void initPresenter() {
        if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
            ShowAlert.closeProgresDialog();
        }
        ShowAlert.showProgresDialog(TesMinatActivity.this);
        tesMinatPresenter = new TesMinatPresenter(this);
        tesMinatPresenter.showTesMinatQuestion();
    }


    @Override
    public void onFailedShowTesMinatQuestion(String message, boolean b) {
        ShowAlert.closeProgresDialog();
        if(b){
            alertDialogReset(message);
        }else{
            builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Terjadi kesalahan. Apakah anda ingin mengulang?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                        ShowAlert.closeProgresDialog();
                    }
                    ShowAlert.showProgresDialog(TesMinatActivity.this);
                    tesMinatPresenter.showTesMinatQuestion();
                }
            });

            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    TesMinatActivity.super.onBackPressed();
                }
            });

            alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onSuccessShowTesMinatQuestion(List<TesMinat> tesMinatList) {
        ShowAlert.closeProgresDialog();
        isTest = true;
        fabPreviousQuestion.setVisibility(View.GONE);
        rlTesMinat.setVisibility(View.VISIBLE);
        tesMinatArrayList.addAll(tesMinatList);
        if(!tesMinatArrayList.isEmpty() && tesMinatArrayList != null){
            tvQuestionA.setText(tesMinatArrayList.get(0).getPernyataanA());
            tvQuestionB.setText(tesMinatList.get(0).getPernyataanB());
            tvQuestionNumber.setText(questionNumber+"/60");
        }
    }

    @Override
    public void onFailedResetTesMinat(String message) {
        ShowAlert.closeProgresDialog();
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Terjadi kesalahan. Apakah anda ingin mengulang?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(TesMinatActivity.this);
                tesMinatPresenter.resetTesMinat();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                TesMinatActivity.super.onBackPressed();
            }
        });

        alert = builder.create();
        alert.show();
    }

    @Override
    public void onSuccessResetTesMinat() {
        tesMinatPresenter.showTesMinatQuestion();
    }

    @Override
    public void onFailedSubmitTesMinat(String message) {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               alert.dismiss();
            }
        });


        alert = builder.create();
        alert.show();
    }

    @Override
    public void onSuccessSubmitTesMinat(String message) {
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

    public void alertDialogReset(String message){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(TesMinatActivity.this);
                tesMinatPresenter.resetTesMinat();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                TesMinatActivity.super.onBackPressed();
            }
        });

        alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isTest){
                    builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setMessage("Seluruh jawaban anda akan hilang. Apakah anda ingin tetap keluar?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           onBackPressed();
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert = builder.create();
                    alert.show();
                }else{
                    super.onBackPressed();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
