package com.example.adhit.bikubiku.ui.ruangBelajarChatting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.adhit.bikubiku.presenter.WaitingRequestResponPresenter;
import com.example.adhit.bikubiku.ui.waitingrequestresponse.WaitingRequestResponView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.qiscus.sdk.data.model.NotificationClickListener;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;
import com.qiscus.sdk.Qiscus;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roy on 1/18/2018.
 */

public class OnNewCommentReceived implements WaitingRequestResponView {

    public Context context;
    private WaitingRequestResponPresenter waitingRequestResponPresenter;
    private int trxStatus = -1;

    public OnNewCommentReceived(Context ctx) {
        this.context = ctx;
    }

    public void endChatTrigger(QiscusComment qiscusComment){
        String strPayload = qiscusComment.getExtraPayload();

        try {
            JSONObject jsonObjectPayload = new JSONObject(strPayload);
            JSONObject jsonObjectContent = jsonObjectPayload.getJSONObject("content");
            System.out.println("extra comment : " + jsonObjectPayload);
            String type = jsonObjectPayload.getString("type");
            String description = jsonObjectContent.getString("description");
            String layanan = jsonObjectContent.getString("layanan");
            String invoice = jsonObjectContent.getString("invoice");
            if ((type.equals("end_chat") && description.equals("Sesi Chat Berakhir"))){
                SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Qiscus.getChatConfig()
                            .setEnableReplyNotification(false)
                            .setNotificationClickListener(new NotificationClickListener() {
                                @Override
                                public void onClick(Context context, QiscusComment qiscusComment) {
                                    QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusComment.getRoomId()),
                                            new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                                                @Override
                                                public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                                                    Intent intent = RuangBelajarChatting.generateIntent(context, qiscusChatRoom, true);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intent);
                                                }
                                                @Override
                                                public void onError(Throwable throwable) {
                                                    throwable.printStackTrace();
                                                }
                                            });
                                }
                            });
                }
            }
            else {
                SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Qiscus.getChatConfig()
                            .setEnableReplyNotification(true)
                            .setNotificationClickListener(new NotificationClickListener() {
                                @Override
                                public void onClick(Context context, QiscusComment qiscusComment) {
                                    QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusComment.getRoomId()),
                                            new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                                                @Override
                                                public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                                                    Intent intent = RuangBelajarChatting.generateIntent(context, qiscusChatRoom, false);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intent);
                                                }
                                                @Override
                                                public void onError(Throwable throwable) {
                                                    throwable.printStackTrace();
                                                }
                                            });
                                }
                            });
                }
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
                toRoomIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
