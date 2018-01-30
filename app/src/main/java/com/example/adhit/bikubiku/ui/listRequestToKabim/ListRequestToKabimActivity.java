package com.example.adhit.bikubiku.ui.listRequestToKabim;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.ListRequestToKabimAdapter;
import com.example.adhit.bikubiku.data.model.RequestToKabim;
import com.example.adhit.bikubiku.presenter.ListRequestToKabimPresenter;
import com.example.adhit.bikubiku.presenter.RuangBelajarPresenter;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.receiver.RequestReceiver;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChatting;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListRequestToKabimActivity extends AppCompatActivity implements ListRequestToKabimView, RequestReceiver.ICheckListRequest, ListRequestToKabimAdapter.IRejectOrCancel, RuangBelajarView {


    private RecyclerView rvRequest;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListRequestToKabimPresenter listRequestToKabimPresenter;
    private RuangBelajarPresenter ruangBelajarPresenter;
    private ListRequestToKabimAdapter adapter;
    private Toolbar toolbar;
    private RequestReceiver mBroadcast;
    private int idRoom = -1;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_request_to_kabim);
        toolbar = findViewById(R.id.toolbar);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Permintaan Mengajar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        rvRequest = findViewById(R.id.rv_request);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        doTheAutoRefresh();
//        registerReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
//        unregisterReceiver(mBroadcast);
    }

    public void initView(){
        ruangBelajarPresenter = new RuangBelajarPresenter(this);
        listRequestToKabimPresenter = new ListRequestToKabimPresenter(this);
        listRequestToKabimPresenter.getListRequest(this);
        adapter = new ListRequestToKabimAdapter(this);
        adapter.setButtonOnClick(this);
        rvRequest.setAdapter(adapter);
        rvRequest.setLayoutManager(new LinearLayoutManager(this));
    }

    private void doTheAutoRefresh() {
        handler.postDelayed(() -> {
            listRequestToKabimPresenter.getListRequest(this);
            adapter.setButtonOnClick(this);
            rvRequest.setAdapter(adapter);
//            initView(); // this is where you put your refresh code
            doTheAutoRefresh();
        }, 2000);
    }

//    public void registerReceiver() {
//        mBroadcast = new RequestReceiver(this);
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(EndChatStatusReceiver.TAG);
//        registerReceiver(mBroadcast, filter);
//    }

    @Override
    public void setListRequestToKabim(List<RequestToKabim> requestToKabimList) {
        adapter.setData(requestToKabimList);
//        this.reqSize = requestToKabimList.size();
    }

    @Override
    public void showError(String s) {
        ShowAlert.showToast(this, s);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onHandleListRequest(List<RequestToKabim> requestToKabimList) {
//        adapter.setData(requestToKabimList);
//        initView();
    }

    @Override
    public void onCancelClicked(String layanan, String invoice, String status) {
        ruangBelajarPresenter.updateStatusTransaksiPR(this,layanan,invoice,-1,status);
    }

    @Override
    public void onAcceptClicked(String layanan, String invoice, String idBiquers, String name, String status) {
        ruangBelajarPresenter.createRoomChat(this,Integer.parseInt(idBiquers),name,layanan,invoice,status);
        System.out.println(""+idRoom);
    }

    @Override
    public void setupCustomChatConfig() {

    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void openChatRoom(QiscusChatRoom qiscusChatRoom) {
        Intent intent = RuangBelajarChatting.generateIntent(ListRequestToKabimActivity.this, qiscusChatRoom, false);
        startActivity(intent);
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
        this.idRoom = idRoom;
    }
}
