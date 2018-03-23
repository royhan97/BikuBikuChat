package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.service.ChattingPsychologyServiceView;
import com.google.gson.JsonObject;

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

    public void finishChatFromService(){
        JSONObject payload = new JSONObject();
        try {
            payload.put("locked", "halo")
                    .put("description", "Sesi Chat ditutup");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        QiscusComment comment = QiscusComment.generateCustomMessage("Sesi Chat Ditutup", "closed_chat", payload,
//                Integer.toString(SavePsychologyConsultationRoomChat.getInstance().getPsychologyConsultationRoomChat()),
//                qiscusChatRoom.getLastTopicId());
//        SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
//        QiscusApi.getInstance().postComment(comment);
        RetrofitClient.getInstance().getApiQiscus()
                .sendMessage(SaveUserData.getInstance().getUser().getId(),
                        Integer.toString(SaveUserData.getInstance().getPsychologyConsultationRoomChat()),
                        "Sesi Chat Ditutup", payload, "custom")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            chattingPsychologyServiceView.onSuccessSendFinishMessage();
                            SaveUserData.getInstance().setRoomChatPsychologyConsultationIsBuild(false);

                        }else{
                            finishChatFromService();
                            chattingPsychologyServiceView.onFailureSendFinishMessage();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        finishChatFromService();
                        chattingPsychologyServiceView.onFailureSendFinishMessage();
                    }
                });

    }
}
