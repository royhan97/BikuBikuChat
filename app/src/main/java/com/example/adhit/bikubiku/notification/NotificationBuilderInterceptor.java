package com.example.adhit.bikubiku.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.model.QiscusNotificationBuilderInterceptor;
import com.qiscus.sdk.util.QiscusTextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.qiscus.sdk.util.QiscusPushNotificationUtil.KEY_NOTIFICATION_REPLY;

/**
 * Created by adhit on 12/01/2018.
 */

public class NotificationBuilderInterceptor  implements QiscusNotificationBuilderInterceptor {


    @Override
    public boolean intercept(NotificationCompat.Builder notificationBuilder, QiscusComment qiscusComment) {
//        String strPayload = qiscusComment.getExtraPayload();
//        if (strPayload != null || !strPayload.equals("null")){
//            try {
//                JSONObject jsonObjectPayload = new JSONObject(strPayload);
//                JSONObject jsonObjectContent = jsonObjectPayload.getJSONObject("content");
//                System.out.println("extra comment : " + jsonObjectPayload);
//                String type = jsonObjectPayload.getString("type");
//                String description = jsonObjectContent.getString("description");
////                    String layanan = jsonObjectContent.getString("layanan");
////                    String invoice = jsonObjectContent.getString("invoice");
//                if ((type.equals("end_chat") && description.equals("Sesi Chat Berakhir"))){
//                    SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, true);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        Qiscus.getChatConfig()
//                                .setEnableReplyNotification(false);
//                    }
//                    Intent intent = new Intent();
//                    intent.setAction(EndChatStatusReceiver.TAG);
//                    intent.putExtra("status_end_chat",true);
//                    BikuBiku.getContext().sendBroadcast(intent);
//                }
//                else {
//                    SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, false);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !Qiscus.getChatConfig().isEnableReplyNotification()) {
//                        Qiscus.getChatConfig()
//                                .setEnableReplyNotification(true);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        if(SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild()){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                Qiscus.getChatConfig()
//                        .setEnableReplyNotification(false);
//            }
//
//        }

        if(SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild()){

            PendingIntent pendingIntent;
            Intent openIntent = new Intent("com.qiscus.OPEN_COMMENT_PN");
            openIntent.putExtra("data", qiscusComment);
            pendingIntent = PendingIntent.getBroadcast(BikuBiku.getContext(), qiscusComment.getRoomId(), openIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            String getRepliedTo = qiscusComment.getSender();
            RemoteInput remoteInput = new RemoteInput.Builder(KEY_NOTIFICATION_REPLY)
                    // .setLabel(QiscusTextUtil.getString(com.qiscus.sdk.R.string.qiscus_reply_to, getRepliedTo.toUpperCase()))
                    .setLabel("REPLY TO "+ qiscusComment.getSender().toUpperCase())
                    .build();

            NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
                    QiscusTextUtil.getString(com.qiscus.sdk.R.string.qiscus_reply_to, getRepliedTo.toUpperCase()), pendingIntent)
                    .addRemoteInput(remoteInput)
                    .build();
            notificationBuilder.addAction(replyAction);

        }

        notificationBuilder.setContentText(qiscusComment.getMessage());
        notificationBuilder.setContentTitle(qiscusComment.getSender());

        return true;
    }




}
