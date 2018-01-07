package com.example.adhit.bikubiku;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.qiscus.sdk.Qiscus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by adhit on 03/01/2018.
 */

public class BikuBiku extends Application{
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initQiscus();
        EventBus.builder().build();
        sContext = this;
    }

    public void initQiscus(){
        Qiscus.init(this, "bikubiku-lntq5ndpvich");

        Qiscus.getChatConfig()
                .setSwipeRefreshColorScheme(R.color.colorPrimary, R.color.colorAccent)
                //.setLeftBubbleColor(R.color.leftBubble)
                .setLeftBubbleTextColor(R.color.qiscus_primary_text)
                .setLeftBubbleTimeColor(R.color.qiscus_secondary_text)
                .setLeftLinkTextColor(R.color.qiscus_primary_text)
                .setLeftProgressFinishedColor(R.color.colorPrimary)
                //.setRightBubbleColor(R.color.colorPrimaryLight)
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
                //.setCancelRecordIcon(R.drawable.ic_cancel_record)
                .setEnablePushNotification(false);
                //.setInlineReplyColor(R.color.colorPrimaryLight);

        Qiscus.getChatConfig().setEnableFcmPushNotification(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Qiscus.getChatConfig().setEnableReplyNotification(true);
        }
    }

}
