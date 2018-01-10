package com.example.adhit.bikubiku.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.ChatPschologyAdapter;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyFragment;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.presenter.QiscusChatPresenter;
import com.qiscus.sdk.ui.adapter.QiscusBaseChatAdapter;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ChattingPsychologyService extends IntentService implements ChattingPsychologyView {
    public static String EXTRA_DURATION = "extra_duration";
    public static String QISCUS_CHAT_ROOM = "qiscus_chat_room";
    public static final String TAG = "DicodingIntentService";
    private ChattingPsychologyPresenter chattingPsychologyPresenter;
    private QiscusChatRoom qiscusChatRoom;

    public ChattingPsychologyService() {
        super("DicodingIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent()");
        if (intent != null) {
            chattingPsychologyPresenter = new ChattingPsychologyPresenter(this);
            int duration = intent.getIntExtra(EXTRA_DURATION, 0);
            qiscusChatRoom  = intent.getParcelableExtra(QISCUS_CHAT_ROOM);

            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            chattingPsychologyPresenter.finishChatFromService(qiscusChatRoom );
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
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

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Konsultasi telah selesai")
                .setContentText("Waktu Konsultasi anda telah berakhir")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
