package com.example.adhit.bikubiku.presenter;

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
import com.example.adhit.bikubiku.data.model.RequestToKabim;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.listRequestToKabim.ListRequestToKabimActivity;
import com.example.adhit.bikubiku.ui.listRequestToKabim.ListRequestToKabimView;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChattingService.NOTIF_CHANNEL_ID;

/**
 * Created by roy on 1/22/2018.
 */

public class ListRequestToKabimPresenter {

    public static final String TEMP_SIZE = "TEMP_SIZE";

    private final ListRequestToKabimView listRequestToKabimView;
    private List<RequestToKabim> requestToKabimList;
    private HashMap<String, String> mapUserProfileName;
    private HashMap<String, String> mapUserProfileAvatar;


    public ListRequestToKabimPresenter(ListRequestToKabimView listRequestToKabimView) {
        this.listRequestToKabimView = listRequestToKabimView;
    }

    public void getListRequest(Context context){
        RetrofitClient.getInstance()
                .getApi()
                .transaksiKabim()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            requestToKabimList = new ArrayList<>();
                            mapUserProfileName = new HashMap<>();
                            mapUserProfileAvatar = new HashMap<>();
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if (status){
                                JsonArray result = body.get("result").getAsJsonArray();

                                for (int i=0;i<result.size();i++){
                                    JsonObject requestJson = result.get(i).getAsJsonObject();
                                    if (requestJson.get("status_trx").getAsInt() == 0){
                                        String idBiquers = requestJson.get("id_biquers").getAsString();
                                        Type type = new TypeToken<RequestToKabim>(){}.getType();
                                        requestToKabimList.add(new Gson().fromJson(requestJson,type));
//                                    getUserProfile(idBiquers);
                                    }
                                }

                                listRequestToKabimView.setListRequestToKabim(requestToKabimList);

                                if (requestToKabimList.size() > 0 && requestToKabimList.size() != SharedPrefUtil.getInt(TEMP_SIZE)){
                                    sendNotification(context,"Anda mendapat "+requestToKabimList.size()+" request baru", "Klik untuk melakukan aksi");
                                }

                                SharedPrefUtil.saveInt(TEMP_SIZE,requestToKabimList.size());
                                requestToKabimList = null;
//                            mapUserProfileName = null;
//                            mapUserProfileAvatar = null;
                            }
                            else {
                                listRequestToKabimView.showError("error pada get list request");
                            }

                        }
                        else {
                            listRequestToKabimView.showError("no data received");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        listRequestToKabimView.showError("on failure");
                    }
                });
    }

    public void getUserProfile(String idUser){
        RetrofitClient.getInstance()
                .getApiQiscus()
                .getUserProfile(idUser)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if (status){
                                JsonObject result = body.get("results").getAsJsonObject();
                                JsonObject userObj = result.get("user").getAsJsonObject();
                                String userName = userObj.get("username").getAsString();
                                String urlAvatar = userObj.get("avatar_url").getAsString();
                                mapUserProfileName.put(idUser, userName);
                                mapUserProfileAvatar.put(idUser, urlAvatar);
                            }
                            else {
                                listRequestToKabimView.showError(message);
                            }
                        }
                        else {
                            listRequestToKabimView.showError("no data user received");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        listRequestToKabimView.showError("on failure get user profile");
                    }
                });
    }

    private void sendNotification(Context context, String title, String messageBody){

        Intent intent = new Intent(context,ListRequestToKabimActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(NOTIF_CHANNEL_ID) == null) {
                createNotifChannel(context);
            }
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setChannelId(NOTIF_CHANNEL_ID);

        notificationManager.notify(0,notificationBuilder.build());

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
        Toast.makeText(context, "createNotifChannel: created=" + NOTIF_CHANNEL_ID, Toast.LENGTH_SHORT).show();
    }
}
