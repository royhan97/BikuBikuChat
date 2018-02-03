package com.example.adhit.bikubiku.ui.waitingrequestresponse;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.presenter.RuangBelajarPresenter;
import com.example.adhit.bikubiku.presenter.WaitingRequestResponPresenter;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.service.ChattingService;
import com.example.adhit.bikubiku.service.ChattingServiceRuangBelajar;
import com.example.adhit.bikubiku.service.RuangBelajarEndChattingService;
import com.example.adhit.bikubiku.ui.loadingtransaction.LoadingTransactionActivity;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChatting;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.util.Date;

/**
 * Created by roy on 1/24/2018.
 */

public class WaitingRequestResponActivity extends AppCompatActivity implements EndChatStatusReceiver.PeriodicCheckCarsReceiverListener, WaitingRequestResponView, RuangBelajarView, View.OnClickListener {

    TextView txtWaiting,txtEndWaiting;
    Button btnReqAgain;
    private String layanan,invoice;
    private EndChatStatusReceiver mBroadcast;
    private final Handler handler = new Handler();
    private WaitingRequestResponPresenter waitingRequestPresenter;
    private  RuangBelajarPresenter ruangBelajarPresenter;
    private boolean isResponseKabim = false;
    private  int animationCounter = 1;
    private Toolbar toolbar;
    private ImageView imgBack,imgCancel, loadingImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_request_response);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtWaiting = findViewById(R.id.txt_waiting);
        txtEndWaiting = findViewById(R.id.txt_end_waiting);
        btnReqAgain = findViewById(R.id.btn_request_again);
        imgBack = findViewById(R.id.img_back);
        imgCancel = findViewById(R.id.img_cancel);
        loadingImage = findViewById(R.id.img_loading);
        waitingRequestPresenter = new WaitingRequestResponPresenter(this);
        ruangBelajarPresenter = new RuangBelajarPresenter(this);

        layanan = getIntent().getStringExtra("layanan");
        invoice = getIntent().getStringExtra("invoice");

        imgBack.setOnClickListener(this);
        imgCancel.setOnClickListener(this);

        startIntentService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        checkStatusTrx();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcast);
    }

    public void startIntentService(){
        Intent intentService = new Intent(this, RuangBelajarEndChattingService.class);
        intentService.putExtra(RuangBelajarEndChattingService.EXTRA_DURATION, 60000);
        startService(intentService);
    }

    public void registerReceiver() {
        mBroadcast = new EndChatStatusReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(EndChatStatusReceiver.TAG);
        registerReceiver(mBroadcast, filter);
    }

    @Override
    public void handleFromEndChat(boolean isEndChat) {

    }

    @Override
    public void handleFromIsRoomEnd(boolean isRoomBuild) {

    }

    @Override
    public void handleFromEndWait(boolean isEndWait) {
        if (isEndWait && !isResponseKabim){
            handler.removeCallbacksAndMessages(null);
            ruangBelajarPresenter.updateStatusTransaksiPR(this,layanan,invoice,-1,"cancel");
            txtWaiting.setVisibility(View.GONE);
            txtEndWaiting.setVisibility(View.VISIBLE);
            btnReqAgain.setVisibility(View.VISIBLE);
            loadingImage.setImageResource(R.drawable.logo);

            btnReqAgain.setOnClickListener(v -> {
                onBackPressed();
                finish();
            });
        }
    }

    private void checkStatusTrx() {
        handler.postDelayed(() -> {
            switch (animationCounter++) {
                case 1:
                    loadingImage.setImageResource(R.drawable.logo);
                    txtWaiting.setText("Menunggu Respon Kabim.");
                    break;
                case 2:
                    loadingImage.setImageResource(R.drawable.logobiku2);
                    txtWaiting.setText("Menunggu Respon Kabim..");
                    break;
                case 3:
                    loadingImage.setImageResource(R.drawable.logo);
                    txtWaiting.setText("Menunggu Respon Kabim...");
                    break;

            }
            animationCounter %= 4;
            if(animationCounter == 0 ) animationCounter = 1;
            waitingRequestPresenter.getDetailTrx(layanan,invoice); // this is where you put your refresh code
            Log.d("WaitingRequestRespon","lagi cek detail trx");
            checkStatusTrx();
        }, 2000);
    }

    @Override
    public void showError(String s) {
        ShowAlert.showToast(this, s);
    }

    @Override
    public void getStatusAndIdRoom(int statusTrx, int idRoom) {
        if (statusTrx == 3){
            this.isResponseKabim = true;
            handler.removeCallbacksAndMessages(null);
            ruangBelajarPresenter.updateStatusTransaksiPR(WaitingRequestResponActivity.this,layanan,invoice,-1,"cancel");

            txtWaiting.setVisibility(View.GONE);
            txtEndWaiting.setVisibility(View.VISIBLE);
            btnReqAgain.setVisibility(View.VISIBLE);

            btnReqAgain.setOnClickListener(this);
        }
        else if(statusTrx == 2 && !isResponseKabim){
            this.isResponseKabim = true;
            ShowAlert.showToast(this, "request anda diterima kabim");
            SharedPrefUtil.saveInt(Constant.ROOM_ID, idRoom);
            SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING,false);
            ruangBelajarPresenter.openRoomChatById(WaitingRequestResponActivity.this,idRoom);
        }
    }

    @Override
    public void setupCustomChatConfig() {

    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void openChatRoom(QiscusChatRoom qiscusChatRoom) {
        if (!SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
//            Intent intentService = new Intent(WaitingRequestResponActivity.this, RuangBelajarEndChattingService.class);
//            intentService.putExtra(RuangBelajarEndChattingService.EXTRA_DURATION, 180000);
//            intentService.putExtra(RuangBelajarEndChattingService.QISCUS_CHAT_ROOM, qiscusChatRoom);
//            startService(intentService);
            Session.getInstance().setBuildRoomRuangBelajar(false);
            waitingRequestPresenter.saveEndTimeTrx();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !Qiscus.getChatConfig().isEnableReplyNotification()) {
                Qiscus.getChatConfig().setEnableReplyNotification(true);
            }

            Intent intent = RuangBelajarChatting.generateIntent(WaitingRequestResponActivity.this, qiscusChatRoom, false);
            startActivity(intent);
            finish();
            ShowAlert.closeProgresDialog();

            Intent intent1= new Intent(this, ChattingServiceRuangBelajar.class);
            startService(intent1);
        }

    }

    @Override
    public void showMessageClosedChatFromService(String status) {

    }

    @Override
    public void onUserOnline(String user, boolean isOnline, Date lastActive) {

    }

    @Override
    public void openWaitingActivity() {

    }

    @Override
    public void setIdRoom(int idRoom) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_request_again){
            onBackPressed();
            finish();
        }
        if(v.getId() == R.id.img_back){
            super.onBackPressed();
        }
        if(v.getId() == R.id.img_cancel){
            AlertDialog alertDialog = new AlertDialog.Builder(WaitingRequestResponActivity.this).create();
            alertDialog.setTitle(getResources().getString(R.string.text_cancel_transaction_title));
            alertDialog.setMessage(getResources().getString(R.string.text_cancel_transaction_message));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ya",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            isResponseKabim = true;
                            ruangBelajarPresenter.updateStatusTransaksiPR(WaitingRequestResponActivity.this,layanan,invoice,-1,"cancel");

                            txtWaiting.setVisibility(View.GONE);
                            txtEndWaiting.setVisibility(View.VISIBLE);
                            btnReqAgain.setVisibility(View.VISIBLE);

                            btnReqAgain.setOnClickListener(v1 -> {
                                onBackPressed();
                                finish();
                            });
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Tidak", (dialogInterface, i) -> alertDialog.dismiss());
            alertDialog.show();

        }
    }
}
