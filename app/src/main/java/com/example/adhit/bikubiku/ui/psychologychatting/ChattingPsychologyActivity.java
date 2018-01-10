package com.example.adhit.bikubiku.ui.psychologychatting;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.presenter.ChangePasswordPresenter;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.receiver.CheckRoomIsBuildReceiver;
import com.example.adhit.bikubiku.service.CheckRoomIsBuildService;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;

import java.util.Date;

public class ChattingPsychologyActivity extends QiscusBaseChatActivity implements ChattingPsychologyView, View.OnClickListener {

    private TextView mTitle, mSubtitle, tvFinish;
    private QiscusCircularImageView qiscusCircularImageView;
    private ChattingPsychologyPresenter chattingPsychologyPresenter;
    private static boolean isHistory1;


    public static Intent generateIntent(Context context, QiscusChatRoom qiscusChatRoom, boolean isHistory) {
        Intent intent = new Intent(context, ChattingPsychologyActivity.class);
        intent.putExtra(CHAT_ROOM_DATA, qiscusChatRoom);
        isHistory1 = isHistory;
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_chatting_psychology;
    }

    @Override
    protected void onLoadView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chattingPsychologyPresenter = new ChattingPsychologyPresenter(this);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mSubtitle = findViewById(R.id.tv_subtitle);
        tvFinish = findViewById(R.id.tv_finish);
        qiscusCircularImageView = findViewById(R.id.profile_picture);
        findViewById(com.qiscus.sdk.R.id.back).setOnClickListener(v -> onBackPressed());
        tvFinish.setOnClickListener(this);


    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return ChattingPsychologyFragment.newInstance(qiscusChatRoom, isHistory1);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        mTitle.setText(qiscusChatRoom.getName());
        qiscusCircularImageView.setImageUrl(qiscusChatRoom.getAvatarUrl());
    }

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {
        if(online){
            mSubtitle.setText(getResources().getString(R.string.text_online));
        }else{
            mSubtitle.setText("");
        }
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if(!typing){
            mSubtitle.setText(getResources().getString(R.string.text_typing));
        }
    }



    @Override
    public void sendFirstMessage(QiscusComment comment) {

    }

    @Override
    public void canCreateRoom(boolean b) {

    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {

    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void showMessageClosedChatFromService(String success) {

    }

    @Override
    public void onClick(View view) {
    }
}
