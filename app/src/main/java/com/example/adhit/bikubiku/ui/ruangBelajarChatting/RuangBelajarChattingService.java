package com.example.adhit.bikubiku.ui.ruangBelajarChatting;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
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
 * Created by adhit on 01/01/2018.
 */

public class RuangBelajarChattingService extends FirebaseMessagingService {

    public static final String NOTIF_CHANNEL_ID = "myChannel";
    private boolean isHistory = false;

    @Override
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
                JSONObject jsonObjectPayload = jsonObject.getJSONObject("payload");
                JSONObject jsonObjectContent = jsonObjectPayload.getJSONObject("content");
                String typePayload = jsonObjectPayload.getString("type");
                String description = jsonObjectContent.getString("description");
                System.out.println("type payload : " + typePayload);
                System.out.println("description : " + description);
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

//                if (typePayload.equals("end_chat") && description.equals("Sesi Chat Berakhir")){
//                    isHistory = true;
//                    SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, true);
//                }
//                else {
//                    SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, false);
//                }


                sendNotification(pengirim, pesan, qiscusChatRoom, isHistory);

            } catch (JSONException e) {
                System.out.println("state disini");
                e.printStackTrace();
            }


            return;
        }
    }

    /**
     * this method is only generating push notification
     */
    private void sendNotification(String title, String messageBody, QiscusChatRoom qiscusChatRoom, boolean isHistory){

        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusChatRoom.getId()),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {

                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        Intent intent = RuangBelajarChatting.generateIntent(getApplicationContext(), qiscusChatRoom, isHistory);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_ONE_SHOT);
                        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (notificationManager.getNotificationChannel(NOTIF_CHANNEL_ID) == null) {
                                createNotifChannel(getApplicationContext());
                            }
                        }

                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle(title)
                                .setContentText(messageBody)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setChannelId(NOTIF_CHANNEL_ID)
                                .setContentIntent(pendingIntent);

                        notificationManager.notify(0,notificationBuilder.build());
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotifChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,
                "MyApp events", NotificationManager.IMPORTANCE_LOW);
        // Configure the notification channel
        channel.setDescription("MyApp event controls");
        channel.setShowBadge(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager manager = context.getSystemService(NotificationManager.class);

        manager.createNotificationChannel(channel);
        Toast.makeText(this, "createNotifChannel: created=" + NOTIF_CHANNEL_ID, Toast.LENGTH_SHORT).show();
    }
}