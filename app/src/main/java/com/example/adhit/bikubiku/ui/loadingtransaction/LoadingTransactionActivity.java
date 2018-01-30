package com.example.adhit.bikubiku.ui.loadingtransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.presenter.TransactionPresenter;
import com.example.adhit.bikubiku.receiver.CreateTransactionReceiver;
import com.example.adhit.bikubiku.service.ChattingService;
import com.example.adhit.bikubiku.service.CreateTransactionService;
import com.example.adhit.bikubiku.ui.detailpsychologist.TransactionView;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

public class LoadingTransactionActivity extends AppCompatActivity implements CreateTransactionReceiver.PeriodicCheckTransactionProcess, ChattingPsychologyView, View.OnClickListener, TransactionView {

    private Toolbar toolbar;
    private ImageView isLoadingImage;
    private TextView tvResultInformation;
    private Button btnGotoPschologist;
    private CreateTransactionReceiver mBroadcast;
    private ChattingPsychologyPresenter chattingPsychologyPresenter;
    private  int animationCounter = 1;
    private Runnable runnable;
    private TransactionPresenter transactionPresenter;
    private Intent mService;
    private ImageView imgBack, imgCancel;
    private TextView tvName;

    @Override
    public void onResume() {

        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_transaction);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerReceiver();
        initPresenter();
        initView();
        initHandler();
    }

    public  void initView(){
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        imgCancel = findViewById(R.id.img_cancel);
        imgCancel.setOnClickListener(this);
        tvName =findViewById(R.id.tv_name);
        tvName.setText(SaveUserData.getInstance().getPsychologistData());
        isLoadingImage = findViewById(R.id.is_image_loading);
        tvResultInformation = findViewById(R.id.tv_result_confirmation);
        btnGotoPschologist = findViewById(R.id.btn_go_to_list_psychology);
        btnGotoPschologist.setOnClickListener(this);
    }

    public void initHandler(){
       runnable = new Runnable() {
            @Override
            public void run() {
                switch (animationCounter++) {
                    case 1:
                        isLoadingImage.setImageResource(R.drawable.logo);
                        break;
                    case 2:
                        isLoadingImage.setImageResource(R.drawable.logobiku2);
                        break;

                }
                animationCounter %= 3;
                if(animationCounter == 0 ) animationCounter = 1;
                isLoadingImage.postDelayed(this, 500);
            }
        };
        isLoadingImage.postDelayed(runnable,500); // set first time for 3 seconds



    }

    public  void initPresenter(){
        chattingPsychologyPresenter = new ChattingPsychologyPresenter(this);
        transactionPresenter = new TransactionPresenter(this);
    }

    public void registerReceiver() {
        mBroadcast = new CreateTransactionReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(CreateTransactionReceiver.TAG);
        registerReceiver(mBroadcast, filter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mService = new Intent(this, CreateTransactionService.class);
        startService(mService);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {

        super.onStop();
        unregisterReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopService(mService);
    }

    private void unregisterReceiver() {
        try {
            if (mBroadcast != null) {
               unregisterReceiver(mBroadcast);
            }
        } catch (Exception e) {
            Log.i("", "broadcastReceiver is already unregistered");
            mBroadcast = null;
        }

    }


    @Override
    public void handleFromReceiver(String idRoom) {
        if(idRoom != null){
            if(idRoom.equals("0")){
                tvResultInformation.setVisibility(View.VISIBLE);
                tvResultInformation.setText("Psikolog yang anda inginkan mungkin sedang sibuk. Anda dapat memilih psikolog lain");
                btnGotoPschologist.setVisibility(View.VISIBLE);
                isLoadingImage.removeCallbacks(runnable);
                isLoadingImage.setImageResource(R.drawable.logo);
                imgCancel.setVisibility(View.GONE);
                unregisterReceiver();
                stopService(mService);
            }
            if(Integer.parseInt(idRoom)>0){
                chattingPsychologyPresenter.openRoomChatById(Integer.parseInt(idRoom));
                unregisterReceiver();
                stopService(mService);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void sendFirstMessage(QiscusComment comment) {

    }

    @Override
    public void canCreateRoom(boolean b) {

    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
//        Intent intent1= new Intent(this, ChattingPsychologyService.class);
//        intent1.putExtra(ChattingPsychologyService.EXTRA_DURATION, 40000);
//        stopService(intent1);
        transactionPresenter.saveEndTimeOfTransaction();
        SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(true);

        Intent intent1= new Intent(this, ChattingService.class);
        startService(intent1);
        stopService(mService);
        Intent intent = ChattingPsychologyActivity.generateIntent(this, qiscusChatRoom, false);
        startActivity(intent);
        finish();
    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void onFailure(String failed) {

    }

    @Override
    public void onSuccessMakeTransaction(String berhasil) {

    }

    @Override
    public void onSuccessChangeTransactionStatus(String berhasil) {
        SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
        SaveUserData.getInstance().removeTransaction();
        super.onBackPressed();
    }

    @Override
    public void onFinishTransaction() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_go_to_list_psychology){
            super.onBackPressed();
        }
        if(view.getId() == R.id.img_back){
            super.onBackPressed();
        }
        if(view.getId() == R.id.img_cancel){
            AlertDialog alertDialog = new AlertDialog.Builder(LoadingTransactionActivity.this).create();
            alertDialog.setTitle(getResources().getString(R.string.text_cancel_transaction_title));
            alertDialog.setMessage(getResources().getString(R.string.text_cancel_transaction_message));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ya",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            stopService(mService);
                            transactionPresenter.changeTransacationStatus("psikologi", SaveUserData.getInstance().getTransaction().getInvoice(), 0, "cancel");

                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Tidak", (dialogInterface, i) -> alertDialog.dismiss());
            alertDialog.show();

        }
    }


}
