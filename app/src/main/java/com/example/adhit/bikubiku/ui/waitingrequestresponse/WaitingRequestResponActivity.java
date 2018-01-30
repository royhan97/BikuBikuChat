package com.example.adhit.bikubiku.ui.waitingrequestresponse;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.presenter.RuangBelajarPresenter;
import com.example.adhit.bikubiku.presenter.WaitingRequestResponPresenter;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.service.RuangBelajarEndChattingService;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChatting;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.util.Date;

/**
 * Created by roy on 1/24/2018.
 */

public class WaitingRequestResponActivity extends AppCompatActivity implements EndChatStatusReceiver.PeriodicCheckCarsReceiverListener, WaitingRequestResponView, RuangBelajarView {

    TextView txtWaiting,txtEndWaiting;
    Button btnReqAgain;
    ProgressBar pbWait;
    private String layanan,invoice;
    private EndChatStatusReceiver mBroadcast;
    private final Handler handler = new Handler();
    private WaitingRequestResponPresenter waitingRequestPresenter;
    private  RuangBelajarPresenter ruangBelajarPresenter;
    private boolean isResponseKabim = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_request_response);
        txtWaiting = findViewById(R.id.txt_waiting);
        txtEndWaiting = findViewById(R.id.txt_end_waiting);
        btnReqAgain = findViewById(R.id.btn_request_again);
        pbWait = findViewById(R.id.pb_waiting);
        waitingRequestPresenter = new WaitingRequestResponPresenter(this);
        ruangBelajarPresenter = new RuangBelajarPresenter(this);

        layanan = getIntent().getStringExtra("layanan");
        invoice = getIntent().getStringExtra("invoice");

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
            ruangBelajarPresenter.updateStatusTransaksiPR(this,layanan,invoice,-1,"cancel");
            pbWait.setVisibility(View.GONE);
            txtWaiting.setVisibility(View.GONE);
            txtEndWaiting.setVisibility(View.VISIBLE);
            btnReqAgain.setVisibility(View.VISIBLE);

            btnReqAgain.setOnClickListener(v -> {
                onBackPressed();
                finish();
            });
        }
    }

    private void checkStatusTrx() {
        handler.postDelayed(() -> {
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
            ruangBelajarPresenter.updateStatusTransaksiPR(WaitingRequestResponActivity.this,layanan,invoice,-1,"cancel");

            pbWait.setVisibility(View.GONE);
            txtWaiting.setVisibility(View.GONE);
            txtEndWaiting.setVisibility(View.VISIBLE);
            btnReqAgain.setVisibility(View.VISIBLE);

            btnReqAgain.setOnClickListener(v -> {
                onBackPressed();
                finish();
            });
        }
        else if(statusTrx == 2){
            this.isResponseKabim = true;
            ShowAlert.showToast(this, "request anda diterima kabim");
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
            Intent intentService = new Intent(WaitingRequestResponActivity.this, RuangBelajarEndChattingService.class);
            intentService.putExtra(RuangBelajarEndChattingService.EXTRA_DURATION, 60000);
            intentService.putExtra(RuangBelajarEndChattingService.QISCUS_CHAT_ROOM, qiscusChatRoom);
            startService(intentService);
        }

        Intent intent = RuangBelajarChatting.generateIntent(WaitingRequestResponActivity.this, qiscusChatRoom, false);
        startActivity(intent);
        finish();
        ShowAlert.closeProgresDialog();
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
}
