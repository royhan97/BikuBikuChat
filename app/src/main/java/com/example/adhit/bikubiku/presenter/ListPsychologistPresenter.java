package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.PsychologistApprove;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.listpsychologist.ListPsychologistView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 07/01/2018.
 */

public class ListPsychologistPresenter {
    private ListPsychologistView psychologyConsultationView;
    public ListPsychologistPresenter(ListPsychologistView psychologyConsultationView){
        this.psychologyConsultationView = psychologyConsultationView;
    }

    public void psychologyList(){
        RetrofitClient.getInstance()
                .getApi()
                .getListPsychologist()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            if(body.get("message").getAsString().equals("Success")){
                                JsonObject results = body.get("result").getAsJsonObject();
                                JsonArray data = results.get("approve").getAsJsonArray();
                                Type type = new TypeToken<List<PsychologistApprove>>(){}.getType();
                                List<PsychologistApprove> psychologistApproveList = new Gson().fromJson(data, type);
                                psychologyConsultationView.showData(psychologistApproveList);
                            }else{
                                psychologyConsultationView.onFailure("Data Not Found");
                            }

                        }else{
                            psychologyConsultationView.onFailure("Data Not Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        psychologyConsultationView.onFailure("Terjadi masalah dengan server");
                    }
                });

    }

    public void isRoomChatBuild(){
        psychologyConsultationView.showBlock(SaveUserData.getInstance().isRoomChatPsychologyConsultationBuild());
    }

    public void openRoomChat(Context context){
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(SaveUserData.getInstance().getPsychologyConsultationRoomChat()),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        psychologyConsultationView.onSuccessOpenRoomChat(qiscusChatRoom);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        psychologyConsultationView.onFailedOpenRoomChat(throwable.toString());
                    }
                });

    }

}
