package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.listpsychologistchattinghistory.ListChattingPsychologistHistoryView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 09/01/2018.
 */

public class ListChattingPsychologistHistoryPresenter {
    private ListChattingPsychologistHistoryView chattingPsychologyHistoryView;

    public ListChattingPsychologistHistoryPresenter(ListChattingPsychologistHistoryView chattingPsychologyHistoryView){
        this.chattingPsychologyHistoryView =chattingPsychologyHistoryView;
    }

    public void getChattingHistoryList(){
        RetrofitClient.getInstance()
                .getApi()
                .getAllTransaction()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();

                            if(status){
                                JsonArray transactionArray = body.get("result").getAsJsonArray();
                                Type type = new TypeToken<List<ChatRoomPsychologyHistory>>(){}.getType();
                                List<ChatRoomPsychologyHistory> chatRoomPsychologyHistoryList =  new Gson().fromJson(transactionArray, type);
                                List<ChatRoomPsychologyHistory> chatRoomPsychologyHistoryList1 = new ArrayList<>();
                                for(int i=0; i<chatRoomPsychologyHistoryList.size(); i++){
                                    if(chatRoomPsychologyHistoryList.get(i).getStatusTrx().equals("1")){
                                        chatRoomPsychologyHistoryList1.add(chatRoomPsychologyHistoryList.get(i));
                                    }
                                }
                                chattingPsychologyHistoryView.showData(chatRoomPsychologyHistoryList1);
                            }else{
                                chattingPsychologyHistoryView.onFailure(body.get("message").getAsString());
                            }
                        }else {
                            chattingPsychologyHistoryView.onFailure("Anda belum pernah melakukan transaksi apapun");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        chattingPsychologyHistoryView.onFailure("Server Bermasalah");
                    }
                });
//
//        RetrofitClient.getInstance().getApiQiscus()
//                .getChatRoomHistory(SaveUserData.getInstance().getUser().getId(), true)
//                .enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                        if(response.isSuccessful()){
//                            JsonObject body = response.body();
//                            JsonObject results = body.get("results").getAsJsonObject();
//                            JsonArray data = results.get("rooms_info").getAsJsonArray();
//                            Type type = new TypeToken<List<ChatRoomPsychologyHistory>>(){}.getType();
//                        }else{
//                            chattingPsychologyHistoryView.onFailure("Data Not Found");
//                            System.out.println(response.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        chattingPsychologyHistoryView.onFailure("Server Error");
//                        System.out.println(t.getMessage());
//                    }
//                });
    }

    public void openRoomChatPsychologyHistoryById(Context context, int id){
        ShowAlert.showProgresDialog(context);
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        chattingPsychologyHistoryView.openRoomChat(qiscusChatRoom);
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        ShowAlert.showToast(context,"Tidak bisa membuka ruang chat");
                        ShowAlert.closeProgresDialog();
                    }
                });


    }

}
