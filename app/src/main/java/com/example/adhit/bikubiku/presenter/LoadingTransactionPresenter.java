package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.ui.loadingtransaction.LoadingTransactionView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUS on 3/23/2018.
 */

public class LoadingTransactionPresenter {
    private LoadingTransactionView loadingTransactionView;

    public LoadingTransactionPresenter (LoadingTransactionView loadingTransactionView){
        this.loadingTransactionView = loadingTransactionView;
    }


    public void openRoomChatById(int id){
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {

                        JSONObject payload = new JSONObject();
                        try {
                            payload.put("locked", "halo").put("description", SaveUserData.getInstance().getUser().getNama() +" ENFP");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        QiscusComment comment = QiscusComment.generateCustomMessage(SaveUserData.getInstance().getUser().getNama()+" ingin berkonsultasi dengan anda", "user_test", payload,
                                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());
                        QiscusApi.getInstance().postComment(comment);
                        loadingTransactionView.onSuccessOpenRoomChat(qiscusChatRoom);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        loadingTransactionView.onFailureOpenRoomChat("Failed" + throwable.getMessage());
                        ShowAlert.closeProgresDialog();
                        openRoomChatById(id);
                    }
                });
    }
}
