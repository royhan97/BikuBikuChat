package com.example.adhit.bikubiku.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.ChatRoomHistory;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChatting;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingHistory.RuangBelajarChattingHistoryView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 1/10/2018.
 */

public class RuangBelajarChattingHistoryPresenter {

    private RuangBelajarChattingHistoryView ruangBelajarChattingHistoryView;

    public RuangBelajarChattingHistoryPresenter(RuangBelajarChattingHistoryView ruangBelajarChattingHistoryView) {
        this.ruangBelajarChattingHistoryView = ruangBelajarChattingHistoryView;
    }

    public void getChattingHistoryList(){
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
                            List<ChatRoomHistory> chatRoomHistories = new ArrayList<>();

                            for(int i=0; i<chatRoomList.size();i++){
                                if(chatRoomList.get(i).getLastCommentMessage().equals("Sesi Chat Berakhir")){
                                    chatRoomHistories.add(chatRoomList.get(i));
                                }
                            }
                            ruangBelajarChattingHistoryView.showData(chatRoomHistories);
                            System.out.println("berhasil");
                        }else{
                            ruangBelajarChattingHistoryView.onFailure(response.toString());
                            System.out.println(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }

    public void openChatRoomHistoryWithId(Context context, int id){
        ShowAlert.showProgresDialog(context);
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        Intent intent = RuangBelajarChatting.generateIntent(context, qiscusChatRoom, true);
                        context.startActivity(intent);
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        ShowAlert.showToast(context,"gagal");
                    }
                });

    }
}
