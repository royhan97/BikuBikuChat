package com.example.adhit.bikubiku.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.receiver.CheckRoomIsBuildChatRoomReceiver;

import java.util.Timer;
import java.util.TimerTask;

public class CheckRoomIsBuildChatRoomService extends Service {
    private static final String TAG = CheckRoomIsBuildChatRoomService.class.getSimpleName();
    private Timer mTimer;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Service");

        requestDataWithInterval();
    }

    private void requestDataWithInterval() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendToReceiver(SaveUserData.getInstance().isRoomChatPsychologyConsultationBuild());
            }
        }, 1, 500);
    }

    private void sendToReceiver(boolean isRoomBuild) {
        Intent intent = new Intent();
        intent.setAction(CheckRoomIsBuildChatRoomReceiver.TAG);
        intent.putExtra("is_room_buildd", isRoomBuild);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        Log.d(TAG, "onDestroy Service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
