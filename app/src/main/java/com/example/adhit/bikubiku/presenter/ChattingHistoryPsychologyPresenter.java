package com.example.adhit.bikubiku.presenter;

import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyFragment;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.example.adhit.bikubiku.ui.psychologychattinghistory.ChattingPsychologyHistoryView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by adhit on 09/01/2018.
 */

public class ChattingHistoryPsychologyPresenter {
    private ChattingPsychologyHistoryView chattingPsychologyHistoryView;

    public ChattingHistoryPsychologyPresenter(ChattingPsychologyHistoryView chattingPsychologyHistoryView){
        this.chattingPsychologyHistoryView =chattingPsychologyHistoryView;
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
                            Type type = new TypeToken<List<ChatRoomPsychologyHistory>>(){}.getType();
                            List<ChatRoomPsychologyHistory> carList = new Gson().fromJson(data, type);
                            for(int i=0; i<carList.size();i++){
                                if(!carList.get(i).getLastCommentMessage().equals("Sesi Chat Ditutup") || carList.get(i).getLastCommentMessage().equals("")){
                                    carList.remove(i);
                                }
                            }
                            chattingPsychologyHistoryView.showData(carList);
                        }else{
                            chattingPsychologyHistoryView.onFailure(response.toString());
                            System.out.println(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }

}
