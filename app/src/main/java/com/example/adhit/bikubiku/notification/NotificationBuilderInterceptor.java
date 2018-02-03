package com.example.adhit.bikubiku.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.model.QiscusNotificationBuilderInterceptor;
import com.qiscus.sdk.util.QiscusTextUtil;

import static com.qiscus.sdk.util.QiscusPushNotificationUtil.KEY_NOTIFICATION_REPLY;

/**
 * Created by adhit on 12/01/2018.
 */

public class NotificationBuilderInterceptor  implements QiscusNotificationBuilderInterceptor {


    @Override
    public boolean intercept(NotificationCompat.Builder notificationBuilder, QiscusComment qiscusComment) {
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
                    "REPLY TO "+ qiscusComment.getSender().toUpperCase(), pendingIntent)
                    .addRemoteInput(remoteInput)
                    .build();
            notificationBuilder.addAction(replyAction);
        }
        notificationBuilder.setContentText(qiscusComment.getMessage());
        notificationBuilder.setContentTitle(qiscusComment.getSender());

        return true;
    }




}
