package com.example.adhit.bikubiku.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserTrxPR;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.presenter.RuangBelajarPresenter;
import com.example.adhit.bikubiku.presenter.TransactionPresenter;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChattingService.NOTIF_CHANNEL_ID;

public class ChattingServiceRuangBelajar extends Service implements RuangBelajarView {
    private TransactionPresenter transactionPresenter;
    NotificationManager notificationManager;
    public static final String ANDROID_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    private Timer mTimer;
    private RuangBelajarPresenter ruangBelajarPresenter;

    public ChattingServiceRuangBelajar() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(ChattingServiceRuangBelajar.class.getSimpleName(),"onCreate Service Ruang Belajar");

        ruangBelajarPresenter = new RuangBelajarPresenter(this);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                periodicCheckTransactionTime();
            }
        }, 10000, 10000);
    }

    public void periodicCheckTransactionTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateandTime = sdf.format(new Date(System.currentTimeMillis()));
        Date date = null;
        try {
            date = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(Session.getInstance().isBuildRoomRuangBelajar()){
            if(calendar.getTimeInMillis() >= SaveUserTrxPR.getInstance().getEndTimeTrx()){
                ruangBelajarPresenter.finishChatFromService();
                Session.getInstance().setBuildRoomRuangBelajar(false);
                SaveUserTrxPR.getInstance().removeEndTimeTrx();
                Intent intentBroadcast = new Intent();
                intentBroadcast.setAction(EndChatStatusReceiver.TAG);
                intentBroadcast.putExtra("status_end_chat", true);
                sendBroadcast(intentBroadcast);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
    public void showMessageClosedChatFromService(String success) {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        System.out.println("Chat Service Ruang Belajar destroyed");
    }
}
