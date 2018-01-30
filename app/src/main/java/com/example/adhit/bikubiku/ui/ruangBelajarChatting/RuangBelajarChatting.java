package com.example.adhit.bikubiku.ui.ruangBelajarChatting;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.service.IsRuangBelajarEndService;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingKabim.RuangBelajarChattingKabimFragment;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.rtc.QiscusRTC;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusPusherApi;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;

import java.util.Date;

public class RuangBelajarChatting extends QiscusBaseChatActivity {
    private TextView mTitle;
    public static TextView mSubtitle;
    private QiscusCircularImageView qiscusCircularImageView;
    private ImageButton imgbVoiceCall;
    private static boolean isHistory;
    private Intent mService;

    public static Intent generateIntent(Context context, QiscusChatRoom qiscusChatRoom, boolean isHistory) {
        RuangBelajarChatting.isHistory = isHistory;
        Intent intent = new Intent(context, RuangBelajarChatting.class);
        intent.putExtra(CHAT_ROOM_DATA, qiscusChatRoom);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.chatting_ruang_belajar;
    }

    @Override
    protected void onLoadView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mSubtitle = findViewById(R.id.tv_subtitle);
        qiscusCircularImageView = findViewById(R.id.profile_picture);
        imgbVoiceCall = findViewById(R.id.imgb_voice_call);
        findViewById(com.qiscus.sdk.R.id.back).setOnClickListener(v -> onBackPressed());


        QiscusRTC.register(Qiscus.getQiscusAccount().getUsername(), Qiscus.getQiscusAccount().getUsername(), "http://dk6kcyuwrpkrj.cloudfront.net/wp-content/uploads/sites/45/2014/05/avatar-blank.jpg");
        QiscusPusherApi.getInstance().listenRoom(qiscusChatRoom);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mService = new Intent(this, IsRuangBelajarEndService.class);
//        startService(mService);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        stopService(mService);
//    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return RuangBelajarFragment.newInstance(qiscusChatRoom, RuangBelajarChatting.isHistory);
    }
    public String generateRoomCall() {
        String room = "callId" + String.valueOf(System.currentTimeMillis());
        return room;
    }
    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        mTitle.setText(qiscusChatRoom.getName());
        qiscusCircularImageView.setImageURI(Uri.parse(qiscusChatRoom.getAvatarUrl()));

        imgbVoiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = generateRoomCall();
                ShowAlert.showToast(RuangBelajarChatting.this, QiscusRTC.getUser());
            }
        });
    }

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {

    }

    @Override
    public void onUserTyping(String user, boolean typing) {

    }
}
