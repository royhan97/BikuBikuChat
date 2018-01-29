package com.example.adhit.bikubiku.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.adhit.bikubiku.data.local.SavePsychologyConsultationRoomChat;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.model.Transaction;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.presenter.TransactionPresenter;
import com.example.adhit.bikubiku.receiver.CreateTransactionReceiver;
import com.example.adhit.bikubiku.ui.detailpsychologist.TransactionView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.List;
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
public class CreateTransactionService extends Service implements TransactionView {
    private TransactionPresenter transactionPresenter;
    private Timer  mTimer, mTimer1;
    private Handler handler;
    public CreateTransactionService() {

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
//        mTimer1 = new Timer();
//        mTimer1.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                transactionPresenter.changeTransacationStatus("psikologi", SaveUserData.getInstance().getTransaction().getInvoice(), "", "cancel");
//            }
//        }, 60000);
        handler=  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
                transactionPresenter.changeTransacationStatus("psikologi", SaveUserData.getInstance().getTransaction().getInvoice(), 0, "cancel");

            }
        }, 60000);
    }




    public void requestDataInterval(){
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
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
                                        JsonObject data = body.get("result").getAsJsonObject();
                                        JsonObject trxObject = data.get("trx").getAsJsonObject();
                                            if(trxObject.get("status_trx").getAsString().equals("2")){
                                                sendToReceiver(trxObject.get("id_room").getAsString());
                                                // SavePsychologyConsultationRoomChat.getInstance().savePsychologyConsultationRoomChat(Integer.parseInt((String) transactionList.get(i).getIdRoom()) );
                                                stopSelf();
                                            }
                                            if(trxObject.get("status_trx").getAsString().equals("3") || trxObject.get("status_trx").getAsString().equals("1")){
                                                sendToReceiver("0");
                                                SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
                                                SaveUserData.getInstance().removeTransaction();
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
        }, 1000, 1000);
    }

    private void sendToReceiver(String idRoom) {
        Intent intent = new Intent();
        intent.setAction(CreateTransactionReceiver.TAG);
        intent.putExtra("id_room_transaction", idRoom);
        System.out.println("id "+ intent.getStringExtra("id_room_transaction"));
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        handler.removeCallbacksAndMessages(null);
        System.out.println("destroy ti");

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
}
