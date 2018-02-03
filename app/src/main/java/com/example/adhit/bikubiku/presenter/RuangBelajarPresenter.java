package com.example.adhit.bikubiku.presenter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SaveUserTrxPR;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.model.TransactionPR;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.service.ChattingServiceRuangBelajar;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChattingService.NOTIF_CHANNEL_ID;

/**
 * Created by roy on 1/4/2018.
 */

public class RuangBelajarPresenter {

    RuangBelajarView ruangBelajarView;

    public RuangBelajarPresenter(RuangBelajarView ruangBelajarView) {
        this.ruangBelajarView = ruangBelajarView;
    }

    public void createTrx(Context context, String layanan, int mapel, int jenjang, int lama, String penjelasan){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .createTrx(layanan,mapel,lama,jenjang,penjelasan)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if (status){
                                JsonObject result = body.get("result").getAsJsonObject();
                                Type type = new TypeToken<TransactionPR>(){}.getType();
                                TransactionPR trxPR = new Gson().fromJson(result, type);
                                SaveUserTrxPR.getInstance().saveTrx(trxPR);
                                ShowAlert.showToast(context, "transaksi berhasil dibuat");
                                ruangBelajarView.openWaitingActivity();
                            }
                            else {
                                ShowAlert.showToast(context,message);
                            }
                        }
                        else {
                            ShowAlert.showToast(context,"tidak bisa membuat transaksi baru");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        ShowAlert.showToast(context, "gagal dalam membuat transaksi baru");
                    }
                });
        ShowAlert.closeProgresDialog();
    }

    public void createRoomChat(Context context, int id, String roomName, String layanan, String invoice, String statusTrx){
        ShowAlert.showProgresDialog(context);
        ArrayList<String> participants = new ArrayList<>();
        participants.add(Integer.toString(id));
        participants.add(SaveUserData.getInstance().getUser().getId());
        RetrofitClient.getInstance().getApiQiscus()
                .createChatRoomSDK(roomName, SaveUserData.getInstance().getUser().getId(), participants)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            int status = body.get("status").getAsInt();
                            if(status == 200){
                                JsonObject userObject = body.get("results").getAsJsonObject();
                                int idRoom = userObject.get("room_id").getAsInt();
                                ruangBelajarView.setIdRoom(idRoom);
                                updateStatusTransaksiPR(context, layanan, invoice, idRoom, statusTrx);
//                                openRoomChatById(idRoom);
                            }else{
                                ShowAlert.showToast(context, "status : "+status);
                                // String message = body.get("message").getAsString();
                                //            loginView.showMessageSnackbar(message);
                                ShowAlert.closeProgresDialog();
                            }
                        }else {
                            ShowAlert.showToast(context, "Can't Create Room");
                            //      loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                            ShowAlert.closeProgresDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        ShowAlert.showToast(context, "Create Room Failed");
                        ShowAlert.closeProgresDialog();
                    }
                });
    }

    public void updateStatusTransaksiPR(Context context, String layanan, String invoice, int id_room, String statusTrx){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .updateStatusTrx(layanan,invoice,id_room,statusTrx)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if (status){
                                ShowAlert.showToast(context,message);
                                if (statusTrx.equals("accept")){
                                    SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING,false);
                                    SharedPrefUtil.saveInt(Constant.ROOM_ID, id_room);
                                    openRoomChatById(context,id_room);
                                }
                                else {
                                    context.stopService(new Intent(context, ChattingServiceRuangBelajar.class));
                                    ShowAlert.closeProgresDialog();
                                }
                            }
                            else {
                                ShowAlert.showToast(context,message);
                                ShowAlert.closeProgresDialog();
                            }
                        }
                        else {
                            ShowAlert.showToast(context,"tidak bisa melakukan update transaksi");
                            ShowAlert.closeProgresDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        ShowAlert.showToast(context, "gagal update transaksi");
                        ShowAlert.closeProgresDialog();
                    }
                });
    }


//    public void createRoomChat(final Context context, String id, String name){
//        ShowAlert.showProgresDialog(context);
//
////        Qiscus.buildChatWith(id)
////                .build(context)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(intent -> {
////                    ruangBelajarView.setupCustomChatConfig();
////                    context.startActivity(intent);
////                    ShowAlert.closeProgresDialog();
////                }, throwable -> {
////                    QiscusErrorLogger.print(throwable);
////                    ShowAlert.showToast(context,QiscusErrorLogger.getMessage(throwable));
////                    ShowAlert.closeProgresDialog();
////                });
//
//        Qiscus.buildChatRoomWith(id)
//                .withTitle(name)
//                .build()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(qiscusChatRoom -> RuangBelajarChatting.generateIntent(context, qiscusChatRoom, false))
//                .subscribe(intent -> {
//                    ruangBelajarView.setupCustomChatConfig();
//                    context.startActivity(intent);
//                    ShowAlert.closeProgresDialog();
//                }, throwable -> {
//                    QiscusErrorLogger.print(throwable);
//                    ShowAlert.showToast(context,QiscusErrorLogger.getMessage(throwable));
//                    ShowAlert.closeProgresDialog();
//                });
//    }

    public void finishChat(QiscusChatRoom qiscusChatRoom){

        JSONObject payload = new JSONObject();

        try {
            payload.put("locked", "halo")
                    .put("description", "Sesi Chat Berakhir")
                    .put("layanan", SaveUserTrxPR.getInstance().getTrx().getLayanan())
                    .put("invoice", SaveUserTrxPR.getInstance().getTrx().getInvoice())
                    .put("idBiquers", SaveUserTrxPR.getInstance().getTrx().getIdBiquers());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        QiscusComment comment = QiscusComment.generateCustomMessage("Sesi Chat Berakhir", "end_chat", payload,
                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());
        ruangBelajarView.sendClosedMessage(comment);

        Session.getInstance().setBuildRoomRuangBelajar(false);

    }

    public void openRoomChatById(Context context, int id){
//        ShowAlert.showProgresDialog(context);
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        ruangBelajarView.openChatRoom(qiscusChatRoom);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        ShowAlert.closeProgresDialog();
                    }
                });
    }

    public void finishChatFromService(){

        JSONObject payload = new JSONObject();
        JSONObject payloadContent = new JSONObject();

        try {
            payloadContent.put("locked", "halo")
                    .put("description", "Sesi Chat Berakhir")
                    .put("layanan", SaveUserTrxPR.getInstance().getTrx().getLayanan())
                    .put("invoice", SaveUserTrxPR.getInstance().getTrx().getInvoice())
                    .put("idBiquers", SaveUserTrxPR.getInstance().getTrx().getIdBiquers());
            payload.put("type", "end_chat")
                    .put("content", payloadContent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Session.getInstance().setBuildRoomRuangBelajar(false);

        RetrofitClient.getInstance().getApiQiscus()
                .sendMessage(SaveUserData.getInstance().getUser().getId(),
                        Integer.toString(SharedPrefUtil.getInt(Constant.ROOM_ID)),
                        "Sesi Chat Berakhir", payload, "custom")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            ruangBelajarView.showMessageClosedChatFromService("success");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        ruangBelajarView.showMessageClosedChatFromService("failed");
                    }
                });
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public void createNotifChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,
                "MyApp events", NotificationManager.IMPORTANCE_LOW);
        // Configure the notification channel
        channel.setDescription("MyApp event controls");
        channel.setShowBadge(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager manager = context.getSystemService(NotificationManager.class);

        manager.createNotificationChannel(channel);
        Toast.makeText(context, "createNotifChannel: created=" + NOTIF_CHANNEL_ID, Toast.LENGTH_SHORT).show();
    }

}
