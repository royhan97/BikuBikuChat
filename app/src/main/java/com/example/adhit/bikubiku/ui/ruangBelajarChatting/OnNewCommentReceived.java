package com.example.adhit.bikubiku.ui.ruangBelajarChatting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserTrxPR;
import com.example.adhit.bikubiku.presenter.WaitingRequestResponPresenter;
import com.example.adhit.bikubiku.ui.waitingrequestresponse.WaitingRequestResponView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.google.firebase.messaging.RemoteMessage;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.service.QiscusFirebaseService;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roy on 1/18/2018.
 */

public class OnNewCommentReceived implements WaitingRequestResponView {

    private WaitingRequestResponPresenter waitingRequestResponPresenter;
    private int trxStatus = -1;

    public void endChatTrigger(QiscusComment qiscusComment){
        String strPayload = qiscusComment.getExtraPayload();
        waitingRequestResponPresenter = new WaitingRequestResponPresenter(this);

        try {
            JSONObject jsonObjectPayload = new JSONObject(strPayload);
            JSONObject jsonObjectContent = jsonObjectPayload.getJSONObject("content");
            System.out.println("extra comment : " + jsonObjectPayload);
            String type = jsonObjectPayload.getString("type");
            String description = jsonObjectContent.getString("description");
            String layanan = jsonObjectContent.getString("layanan");
            String invoice = jsonObjectContent.getString("invoice");
            waitingRequestResponPresenter.getDetailTrx(layanan,invoice);
            if ((type.equals("end_chat") && description.equals("Sesi Chat Berakhir")) && trxStatus == 1){
                SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, true);
            }
            else {
                SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clickNotificationHandler(Context context, QiscusComment qiscusComment){
        String type, description;
        Boolean isHistory = false;
        if (qiscusComment.getExtraPayload() != null){
            try {
                String strPayload = qiscusComment.getExtraPayload();
                JSONObject jsonObjectPayload = new JSONObject(strPayload);
                JSONObject jsonObjectContent = jsonObjectPayload.getJSONObject("content");
                System.out.println("extra comment : " + jsonObjectPayload);
                type = jsonObjectPayload.getString("type");
                description = jsonObjectContent.getString("description");
                if ((type.equals("end_chat") && description.equals("Sesi Chat Berakhir")) || SharedPrefUtil.getBoolean(Constant.IS_END_CHATTING)) {
                    isHistory = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final Boolean finalIsHistory = isHistory;
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusComment.getRoomId()), new QiscusRxExecutor.Listener<QiscusChatRoom>() {
            @Override
            public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                Intent toRoomIntent = RuangBelajarChatting.generateIntent(context, qiscusChatRoom, finalIsHistory);
                toRoomIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(toRoomIntent);
//                ((Activity) context).finish();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void showError(String s) {
        Log.d("OnNewCommentReceived",s);
    }

    @Override
    public void getStatusAndIdRoom(int statusTrx, int idRoom) {
        this.trxStatus = statusTrx;
    }

}
