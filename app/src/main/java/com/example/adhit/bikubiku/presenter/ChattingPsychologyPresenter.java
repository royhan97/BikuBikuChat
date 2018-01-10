package com.example.adhit.bikubiku.presenter;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SavePsychologyConsultationRoomChat;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.service.ChattingPsychologyService;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusErrorLogger;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by adhit on 08/01/2018.
 */

public class ChattingPsychologyPresenter {

    private ChattingPsychologyView chattingPsychologyView;
    public ChattingPsychologyPresenter(ChattingPsychologyView chattingPsychologyView){
        this.chattingPsychologyView = chattingPsychologyView;
    }

    public ChattingPsychologyPresenter() {

    }

    public void createRoomChat(Context context, int id, String roomName){
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
                                SavePsychologyConsultationRoomChat.getInstance().savePsychologyConsultationRoomChat(idRoom);
                               openRoomChatById(idRoom);
                            }else{
                               // String message = body.get("message").getAsString();
                    //            loginView.showMessageSnackbar(message);
                            }
                        }else {
                      //      loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        }
                        ShowAlert.closeProgresDialog();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }

    public void sendFirstMessage(QiscusChatRoom qiscusChatRoom){
        String username = null;
        JSONObject payload = new JSONObject();

        try {
            payload.put("locked", "halo").put("description", SaveUserData.getInstance().getUser().getNama() +" ENFP");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        QiscusComment comment = QiscusComment.generateCustomMessage(SaveUserData.getInstance().getUser().getNama()+" ingin berkonsultasi dengan anda", "user_test", payload,
                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());
        SavePsychologyConsultationRoomChat.getInstance().savePsychologyConsultationRoomChat(qiscusChatRoom.getId());
        SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(true);
        chattingPsychologyView.sendFirstMessage(comment);

    }

    public boolean checkChattingPsychology(){
        return SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild();
    }

    public void finishChat(Context context, QiscusChatRoom qiscusChatRoom){

        JSONObject payload = new JSONObject();

        try {
            payload.put("locked", "halo")
                    .put("description", "Sesi Chat ditutup");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        QiscusComment comment = QiscusComment.generateCustomMessage("Sesi Chat Ditutup *Developed Confirmed By Qiscus and BikuBiku", "closed_chat", payload,
                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());
        chattingPsychologyView.sendClosedMessage(comment);

        SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(false);

    }



    public void openRoomChatById(int id){
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        chattingPsychologyView.canCreateRoom(true);
                        chattingPsychologyView.openRoomChat(qiscusChatRoom);

                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }





}
