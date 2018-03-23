package com.example.adhit.bikubiku.presenter;


import android.content.Context;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by adhit on 08/01/2018.
 */

public class ChattingPsychologyPresenter {

    private ChattingPsychologyView chattingPsychologyView;
    public ChattingPsychologyPresenter(ChattingPsychologyView chattingPsychologyView){
        this.chattingPsychologyView = chattingPsychologyView;
    }

    public void finishChat(Context context, QiscusChatRoom qiscusChatRoom){
        JSONObject payload = new JSONObject();
        try {
            payload.put("locked", "halo")
                    .put("description", "Sesi Chat ditutup");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        QiscusComment comment = QiscusComment.generateCustomMessage("Sesi Chat Ditutup", "closed_chat", payload,
                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());
        SaveUserData.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
        chattingPsychologyView.sendClosedMessage(comment);
        chattingPsychologyView.onFinishTransaction();
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
                        chattingPsychologyView.onFailure("Failed" + throwable.getMessage());
                        ShowAlert.closeProgresDialog();
                        openRoomChatById(id);
                    }
                });
    }





}
