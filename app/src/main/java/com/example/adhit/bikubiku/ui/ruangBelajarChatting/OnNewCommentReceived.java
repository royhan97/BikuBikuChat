package com.example.adhit.bikubiku.ui.ruangBelajarChatting;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.presenter.WaitingRequestResponPresenter;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.ui.waitingrequestresponse.WaitingRequestResponView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
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
            if (strPayload != null || !strPayload.equals("null")){
                try {
                    JSONObject jsonObjectPayload = new JSONObject(strPayload);
                    JSONObject jsonObjectContent = jsonObjectPayload.getJSONObject("content");
                    System.out.println("extra comment : " + jsonObjectPayload);
                    String type = jsonObjectPayload.getString("type");
                    String description = jsonObjectContent.getString("description");
//                    String layanan = jsonObjectContent.getString("layanan");
//                    String invoice = jsonObjectContent.getString("invoice");
                    if ((type.equals("end_chat") && description.equals("Sesi Chat Berakhir"))){
                        SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Qiscus.getChatConfig()
                                    .setEnableReplyNotification(false);
                        }
                        Intent intent = new Intent();
                        intent.setAction(EndChatStatusReceiver.TAG);
                        intent.putExtra("status_end_chat",true);
                        context.sendBroadcast(intent);
                    }
                    else {
                        SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !Qiscus.getChatConfig().isEnableReplyNotification()) {
                            Qiscus.getChatConfig()
                                    .setEnableReplyNotification(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        if(SaveUserData.getInstance().isRoomChatPsychologyConsultationBuild()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Qiscus.getChatConfig()
                        .setEnableReplyNotification(false);
            }

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
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1){
                    toRoomIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    toRoomIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(toRoomIntent);
                }
                else {
                     toRoomIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     context.startActivity(toRoomIntent);
                }
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
