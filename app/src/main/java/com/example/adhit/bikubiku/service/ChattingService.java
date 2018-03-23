package com.example.adhit.bikubiku.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyServicePresenter;
import com.example.adhit.bikubiku.presenter.TransactionPresenter;
import com.example.adhit.bikubiku.ui.detailpsychologist.TransactionView;
import com.example.adhit.bikubiku.ui.home.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ChattingService extends Service implements ChattingPsychologyServiceView, TransactionView {
    private ChattingPsychologyServicePresenter chattingPsychologyServicePresenter;
    private TransactionPresenter transactionPresenter;
    NotificationManager notificationManager;
    public static final String ANDROID_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    private Timer mTimer;

    public ChattingService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(SaveUserData.getInstance().isRoomChatPsychologyConsultationBuild()){
            chattingPsychologyServicePresenter = new ChattingPsychologyServicePresenter(this);
            transactionPresenter = new TransactionPresenter(this);
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    periodicCheckTransactionTime();
                }
            }, 10000, 10000);

        }
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
        if(SaveUserData.getInstance().isRoomChatPsychologyConsultationBuild()){
            if(calendar.getTimeInMillis() >= SaveUserData.getInstance().getEndTimeOfTransaction()){
                chattingPsychologyServicePresenter.finishChatFromService();
                SaveUserData.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
                SaveUserData.getInstance().removeEndTimeOfTransaction();


            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void showMessageClosedChatFromService(String success) {
        transactionPresenter.changeTransacationStatus(
                "Psikologi",
                SaveUserData.getInstance().getTransaction().getInvoice(),
                SaveUserData.getInstance().getPsychologyConsultationRoomChat(),
                "finish");


    }

    @Override
    public void onFailureFinishChat() {
        showNotification();
        chattingPsychologyServicePresenter.finishChatFromService();
    }

    @Override
    public void onFailure(String message) {
//        transactionPresenter.changeTransacationStatus(
//                "Psikologi",
//                SaveUserData.getInstance().getTransaction().getInvoice(),
//                String.valueOf(SavePsychologyConsultationRoomChat.getInstance().getPsychologyConsultationRoomChat()),
//                "finish");
    }

    @Override
    public void onSuccessMakeTransaction(String berhasil) {

    }

    @Override
    public void onSuccessChangeTransactionStatus(String berhasil) {
        showNotification();
    }

    public void showNotification(){
        int idRoom = SaveUserData.getInstance().getPsychologyConsultationRoomChat();
        SaveUserData.getInstance().removePsychologyConsultationRoomChat();
        SaveUserData.getInstance().removeTransaction();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createChannels(notificationManager);
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
                .setChannelId(ANDROID_CHANNEL_ID)
                .setContentIntent(pendingIntent);


        notificationManager.notify(0,notificationBuilder.build());
        notificationManager.cancel(idRoom);

        stopSelf();
    }

    public void createChannels(NotificationManager notificationManager) {

        // create android channel
        NotificationChannel androidChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            androidChannel.setLightColor(Color.GREEN);
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            notificationManager.createNotificationChannel(androidChannel);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        System.out.println("Chat Service destroyed");
    }
}
