package com.example.adhit.bikubiku.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.ChatRoomHistory;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChatting;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingKabim.RuangBelajarChattingKabimView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 1/10/2018.
 */

public class RuangBelajarChattingKabimPresenter {

    private RuangBelajarChattingKabimView ruangBelajarChattingKabimView;

    public RuangBelajarChattingKabimPresenter(RuangBelajarChattingKabimView ruangBelajarChattingKabimView) {
        this.ruangBelajarChattingKabimView = ruangBelajarChattingKabimView;
    }

    public void getActiveChattingBiquers(){
        RetrofitClient.getInstance().getApiQiscus()
                .getChatRoomHistory(SaveUserData.getInstance().getUser().getId(), true)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonObject results = body.get("results").getAsJsonObject();
                            JsonArray data = results.get("rooms_info").getAsJsonArray();
                            Type type = new TypeToken<List<ChatRoomHistory>>(){}.getType();
                            List<ChatRoomHistory> chatRoomList = new Gson().fromJson(data, type);
                            List<ChatRoomHistory> chatRoomKabim = new ArrayList<>();

                            for(int i=0; i<chatRoomList.size();i++){
                                if(!chatRoomList.get(i).getLastCommentMessage().equals("Sesi Chat Berakhir")){
                                    chatRoomKabim.add(chatRoomList.get(i));
                                }
                            }
                            ruangBelajarChattingKabimView.showData(chatRoomKabim);
                            System.out.println("berhasil");
                        }else{
                            ruangBelajarChattingKabimView.onFailure(response.toString());
                            System.out.println(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }

    public void openChatRoomKabimWithId(Context context, int id){
        ShowAlert.showProgresDialog(context);
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        Intent intent = RuangBelajarChatting.generateIntent(context, qiscusChatRoom, false);
                        context.startActivity(intent);
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        ShowAlert.showToast(context,"gagal");
                        ShowAlert.closeProgresDialog();
                    }
                });

    }

}
