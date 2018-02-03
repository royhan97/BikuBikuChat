package com.example.adhit.bikubiku.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.presenter.RuangBelajarPresenter;
import com.example.adhit.bikubiku.receiver.CheckRoomIsBuildReceiver;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.util.Date;

import static com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChattingService.NOTIF_CHANNEL_ID;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RuangBelajarEndChattingService extends IntentService implements RuangBelajarView {
    public static String EXTRA_DURATION = "extra_duration";
    public static String QISCUS_CHAT_ROOM = "qiscus_chat_room";
    public static String TAG = "RuangBelajarChattingService";
    private RuangBelajarPresenter ruangBelajarPresenter;
    private QiscusChatRoom qiscusChatRoom;

    public RuangBelajarEndChattingService() {
        super("RuangBelajarChattingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(TAG, "onHandleIntent()");
            ruangBelajarPresenter = new RuangBelajarPresenter(this);
            int duration = intent.getIntExtra(EXTRA_DURATION, 0);

            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            if (intent.getParcelableExtra(QISCUS_CHAT_ROOM) == null){
                Intent intentBroadcast = new Intent();
                intentBroadcast.setAction(EndChatStatusReceiver.TAG);
                intentBroadcast.putExtra("waiting_end", true);
                sendBroadcast(intentBroadcast);
            }
            else {
                if (!SharedPrefUtil.getBoolean(Constant.IS_END_CHATTING)){
                    qiscusChatRoom  = intent.getParcelableExtra(QISCUS_CHAT_ROOM);
//                    ruangBelajarPresenter.finishChatFromService(qiscusChatRoom);
                    Intent intentBroadcast = new Intent();
                    intentBroadcast.setAction(EndChatStatusReceiver.TAG);
                    intentBroadcast.putExtra("status_end_chat", true);
                    sendBroadcast(intentBroadcast);
                }
            }
        }
    }

//    @Override
//    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        Log.d(TAG,"onStart intent service");
//        return START_STICKY;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() intent service");
    }


    @Override
    public void setupCustomChatConfig() {

    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void openChatRoom(QiscusChatRoom qiscusChatRoom) {

    }

    @Override
    public void showMessageClosedChatFromService(String status) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Belajar Selesai")
                .setContentText("Waktu Belajar Anda telah Berakhir")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(NOTIF_CHANNEL_ID)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(NOTIF_CHANNEL_ID) == null) {
                ruangBelajarPresenter.createNotifChannel(getApplicationContext());
            }
        }
        notificationManager.notify(0,notificationBuilder.build());
        notificationManager.cancel(SharedPrefUtil.getInt(Constant.ROOM_ID));
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