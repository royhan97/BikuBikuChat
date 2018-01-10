package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.service.ChattingPsychologyServiceView;
import com.google.gson.JsonObject;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 10/01/2018.
 */

public class ChattingPsychologyServicePresenter {

    private ChattingPsychologyServiceView chattingPsychologyServiceView;
    public ChattingPsychologyServicePresenter(ChattingPsychologyServiceView chattingPsychologyServiceView){
        this.chattingPsychologyServiceView = chattingPsychologyServiceView;
    }

    public void finishChatFromService(QiscusChatRoom qiscusChatRoom){

        JSONObject payload = new JSONObject();
        JSONObject payloadContent = new JSONObject();

        try {
            payloadContent.put("locked", "halo")
                    .put("description", "Sesi Chat ditutup");

            payload.put("type", "closed_chat")
                    .put("content", payloadContent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
        RetrofitClient.getInstance().getApiQiscus()
                .sendMessage(SaveUserData.getInstance().getUser().getId(),
                        Integer.toString(qiscusChatRoom.getId()),
                        "Sesi Chat Ditutup *Developed Confirmed By Qiscus and BikuBiku", payload, "custom")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            chattingPsychologyServiceView.showMessageClosedChatFromService("success");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        chattingPsychologyServiceView.showMessageClosedChatFromService("failed");
                    }
                });

    }
}
