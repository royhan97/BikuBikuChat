package com.example.adhit.bikubiku.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.presenter.TransactionPresenter;
import com.example.adhit.bikubiku.receiver.CreateTransactionReceiver;
import com.example.adhit.bikubiku.ui.detailpsychologist.TransactionView;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class PsychologistConsultationTransactionService extends Service implements TransactionView {
    private TransactionPresenter transactionPresenter;
    private Timer  mTimer, mTimer1;
    private Handler handler;
    NotificationManager notificationManager;
    public static final String ANDROID_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public PsychologistConsultationTransactionService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        transactionPresenter = new TransactionPresenter(this);
        requestDataInterval();

        handler=  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification("Biku Biku","Transaksi anda dibatalkan",0);
                SaveUserData.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
                transactionPresenter.changeTransacationStatus("psikologi", SaveUserData.getInstance().getTransaction().getInvoice(), 0, "cancel");

            }
        }, 60000);
    }




    public void requestDataInterval(){
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(SaveUserData.getInstance().getTransaction().getInvoice() !=null){
                    RetrofitClient
                            .getInstance()
                            .getApi()
                            .getDetailTrx("psikologi", SaveUserData.getInstance().getTransaction().getInvoice())
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if(response.isSuccessful()){
                                        JsonObject body = response.body();
                                        String message= body.get("message").getAsString();
                                        if(message.equals("Success")){
                                            JsonObject trxObject = body.get("result").getAsJsonObject();
                                            if(trxObject.get("status_trx").getAsString().equals("2")){
                                                Log.d("id room", trxObject.get("id_room").getAsString());
                                                if(!trxObject.get("id_room").getAsString().equals("0")  && trxObject.get("id_room").getAsString() != null ){
                                                    sendToReceiver(trxObject.get("id_room").getAsString());
                                                    SaveUserData.getInstance().savePsychologyConsultationRoomChat(Integer.parseInt(trxObject.get("id_room").getAsString()) );
                                                    showNotification("Biku Biku","Konsultasi anda telah dimulai", Integer.parseInt(trxObject.get("id_room").getAsString()));
                                                    stopSelf();
                                                }
                                            }
                                            if(trxObject.get("status_trx").getAsString().equals("3") || trxObject.get("status_trx").getAsString().equals("1")){
                                                sendToReceiver("0");
                                                SaveUserData.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
                                                SaveUserData.getInstance().removeTransaction();
                                                showNotification("Biku Biku","Transaksi anda dibatalkan", 0);
                                                stopSelf();
                                            }


                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {

                                }
                            });
                }

            }
        }, 1000, 1000);
    }

    private void sendToReceiver(String idRoom) {
        Intent intent = new Intent();
        intent.setAction(CreateTransactionReceiver.TAG);
        intent.putExtra("id_room_transaction", idRoom);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        handler.removeCallbacksAndMessages(null);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFailure(String message) {
        transactionPresenter.changeTransacationStatus( "psikologi", SaveUserData.getInstance().getTransaction().getInvoice(), 0, "cancel");

    }

    @Override
    public void onSuccessMakeTransaction(String berhasil) {

    }

    @Override
    public void onSuccessChangeTransactionStatus(String berhasil) {
        SaveUserData.getInstance().removeTransaction();
        sendToReceiver("0");
        stopSelf();
    }

//    public void sendFirstMessage(){
//        JSONObject payload = new JSONObject();
//        JSONObject payloadContent = new JSONObject();
//
//        try {
//            payloadContent.put("locked", "halo")
//                    .put("description", SaveUserData.getInstance().getUser().getNama() +" ENFP");
//
//            payload.put("type", "user_test")
//                    .put("content", payloadContent);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RetrofitClient.getInstance().getApiQiscus()
//                .sendMessage(SaveUserData.getInstance().getUser().getId()+"b",
//                        Integer.toString(SavePsychologyConsultationRoomChat.getInstance().getPsychologyConsultationRoomChat()),
//                        SaveUserData.getInstance().getUser().getNama()+" ingin berkonsultasi dengan anda", payload, "custom")
//                .enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                    }
//                });
//
//    }

    public void showNotification(String title, String message, int idRoom){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createChannels(notificationManager);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("id_room", idRoom);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(ANDROID_CHANNEL_ID)
                .setContentIntent(pendingIntent);


        notificationManager.notify(idRoom,notificationBuilder.build());

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
}
