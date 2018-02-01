package com.example.adhit.bikubiku;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.example.adhit.bikubiku.ui.notification.NotificationBuilderInterceptor;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyFragment;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.OnNewCommentReceived;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarFragment;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SecretKeyQiscus;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.qiscus.rtc.QiscusRTC;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.event.QiscusChatRoomEvent;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.event.QiscusUserStatusEvent;
import com.qiscus.sdk.util.QiscusRxExecutor;
import com.qiscus.sdk.data.model.NotificationClickListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by adhit on 03/01/2018.
 */

public class BikuBiku extends Application{
    private static Context sContext;
    private OnNewCommentReceived onNewCommentReceived;
    private RuangBelajarFragment ruangBelajarFragment;

    public static Context getContext() {
        return sContext;
    }
//    public static BikuBiku instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initQiscus();
        sContext = this;
//        instance =this;

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
//    public static BikuBiku getInstance(){
//        return  instance;
//    }


    public void initQiscus(){
        Qiscus.init(this, "bikubiku-it3hra928qv7");
        QiscusRTC.init(this, "bikubiku-it3hra928qv7", SecretKeyQiscus.secretKeyQiscus);
        onNewCommentReceived = new OnNewCommentReceived(getApplicationContext());
        ruangBelajarFragment = new RuangBelajarFragment();

        Qiscus.getChatConfig()
                .setSwipeRefreshColorScheme(R.color.colorPrimary, R.color.colorAccent)
                .setLeftBubbleColor(R.color.colorWhite)
                .setNotificationBigIcon(R.drawable.logo)
                .setNotificationSmallIcon(R.drawable.logo)
                .setOnlyEnablePushNotificationOutsideChatRoom(false)
                .setNotificationClickListener(new NotificationClickListener() {
                    @Override
                    public void onClick(Context context, QiscusComment qiscusComment) {
                        if (qiscusComment.getRoomId() == SharedPrefUtil.getInt(Constant.ROOM_ID)){
                            onNewCommentReceived.clickNotificationHandler(context, qiscusComment);
                        }
                        else {
                            QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusComment.getRoomId()),
                                    new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                                        @Override
                                        public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                                            Intent intent = ChattingPsychologyActivity.generateIntent(getApplicationContext(), qiscusChatRoom, false);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                        @Override
                                        public void onError(Throwable throwable) {
                                            throwable.printStackTrace();
                                        }
                                    });
                        }
                    }
                })
                .setNotificationBuilderInterceptor(new NotificationBuilderInterceptor())
                .setLeftBubbleTextColor(R.color.color_black)
                .setLeftBubbleTimeColor(R.color.qiscus_secondary_text)
                .setLeftLinkTextColor(R.color.qiscus_primary_text)
                .setLeftProgressFinishedColor(R.color.colorPrimary)
                .setRightBubbleColor(R.color.colorGreen400)
                .setRightBubbleTextColor(R.color.color_black)
                //.setRightProgressFinishedColor(R.color.colorPrimaryLight)
                .setSelectedBubbleBackgroundColor(R.color.colorPrimary)
                .setReadIconColor(R.color.colorPrimary)
                .setAppBarColor(R.color.colorPrimary)
                .setStatusBarColor(R.color.colorPrimaryDark)
                .setAccentColor(R.color.colorAccent)
                .setAccountLinkingTextColor(R.color.colorPrimary)
               // .setAccountLinkingBackground(R.color.accountLinkingBackground)
                .setButtonBubbleTextColor(R.color.colorPrimary)
                //.setButtonBubbleBackBackground(R.color.accountLinkingBackground)
                .setReplyBarColor(R.color.colorPrimary)
                .setReplySenderColor(R.color.colorPrimary)
                .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off)
                //.setStopRecordIcon(R.drawable.ic_send_record)
                .setEnableAddFile(true)
                .setEnablePushNotification(true)
                .setNotificationSmallIcon(R.drawable.logo)
                .setEnableAvatarAsNotificationIcon(true)
//                .setNotificationClickListener((context, qiscusComment) -> {
//                    onNewCommentReceived.clickNotificationHandler(context, qiscusComment);
//                })
                //.setCancelRecordIcon(R.drawable.ic_cancel_record)
                .setEnableFcmPushNotification(false);
                //.setInlineReplyColor(R.color.colorPrimaryLight);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Qiscus.getChatConfig().
                    setEnableReplyNotification(true);
        }
    }


    @Subscribe
    public void onGetNewQiscusComment(QiscusCommentReceivedEvent event) {
//        onNewCommentReceived.refreshKabimRoomList();
        if (event.getQiscusComment() != null){
            QiscusComment qiscusComment = event.getQiscusComment();
            // Do your implementation
            onNewCommentReceived.endChatTrigger(qiscusComment);
        }
    }

    /**
     * Call QiscusPusherApi.getInstance().listenRoom(qiscusChatRoom); to get room event from anywhere at your application
     */
    @Subscribe
    public void onGetNewQiscusRoomEvent(QiscusChatRoomEvent event) {
        switch (event.getEvent()) {
            case TYPING:
                //Someone is typing on this room event.getRoomId()
                ruangBelajarFragment.onUserTyping(event.getUser(),event.isTyping());
                break;
            case DELIVERED:
                //Someone just received your message event.getCommentId()
                break;
            case READ:
                //Someone just read your message event.getCommentId()
                break;
        }
    }

    /**
     * Call QiscusPusherApi.getInstance().listenUserStatus("user1@gmail.com"); to listen status of user1@gmail.com
     */
    @Subscribe
    public void onUserStatusUpdated(QiscusUserStatusEvent event) {
        // A user just changed his/her status from (online or offline)
        // event.getUser() changed to event.isOnline() at event.getLastActive()
        ruangBelajarFragment.onUserOnline(event.getUser(), event.isOnline(), event.getLastActive());
    }

    @Subscribe
    public void onUserChanged(QiscusChatRoomEvent qiscusChatRoomEvent){
        if(qiscusChatRoomEvent.isTyping()){
            ChattingPsychologyFragment chatFragment = new ChattingPsychologyFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), true);
        }else{
            ChattingPsychologyFragment chatFragment = new ChattingPsychologyFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), false);
        }
    }

    @Subscribe
    public  void onUserTyping(QiscusUserStatusEvent qiscusUserStatusEvent){
        if(qiscusUserStatusEvent.isOnline()){
            ChattingPsychologyFragment.onUserChanged(true);
        }else {
            ChattingPsychologyFragment.onUserChanged(false);
        }
    }

}
