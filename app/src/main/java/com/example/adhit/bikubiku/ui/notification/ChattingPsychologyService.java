package com.example.adhit.bikubiku.ui.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusRoomMember;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.service.QiscusFirebaseService;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by adhit on 08/01/2018.
 */

public class ChattingPsychologyService extends FirebaseMessagingService {
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (QiscusFirebaseService.handleMessageReceived(remoteMessage)) { // For qiscus

            System.out.println(remoteMessage.getData());

            try {
                QiscusChatRoom qiscusChatRoom = new QiscusChatRoom();
                QiscusRoomMember qiscusRoomMember = new QiscusRoomMember();
                ArrayList<QiscusRoomMember> qiscusRoomMemberArrayList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject( remoteMessage.getData().get("payload"));
                String pengirim =jsonObject.getString("username").substring(0, 1).toUpperCase()+ jsonObject.getString("username").substring(1) ;
                String pesan = jsonObject.getString("message");
                String roomType = jsonObject.getString("room_type");
                String member[] = jsonObject.getString("raw_room_name").split(" ");
                for(int i =0; i<member.length;i++){
                    qiscusRoomMember.setEmail(member[i]);
                    qiscusRoomMemberArrayList.add(qiscusRoomMember);
                }

                if(roomType.equals("false")){
                    qiscusChatRoom.setGroup(false);
                }else{
                    qiscusChatRoom.setGroup(true);
                }
                qiscusChatRoom.setMember(qiscusRoomMemberArrayList);
                qiscusChatRoom.setId(Integer.parseInt(remoteMessage.getData().get("qiscus_room_id")));
                qiscusChatRoom.setAvatarUrl(jsonObject.getString("room_avatar"));
                qiscusChatRoom.setLastTopicId(jsonObject.getInt("topic_id_str"));
                qiscusChatRoom.setName(jsonObject.getString("room_name"));

                if(jsonObject.getString("type").equals("file_attachment")){
                    pesan = pengirim + " mengirim sebuah file";
                }
                sendNotification(pengirim, pesan, Integer.parseInt(remoteMessage.getData().get("qiscus_room_id")));

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return;
        }
        //sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        //Your FCM PN here

    }

    /**
     * this method is only generating push notification
     */
    private void sendNotification(String title, String messageBody, int id){

        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        Intent intent = ChattingPsychologyActivity.generateIntent(getApplicationContext(), qiscusChatRoom);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_ONE_SHOT);
                        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle(title)
                                .setContentText(messageBody)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(0,notificationBuilder.build());
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

    }
}
