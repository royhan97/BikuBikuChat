package com.example.adhit.bikubiku.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.receiver.CheckRoomIsBuildReceiver;

import java.util.Timer;
import java.util.TimerTask;

public class CheckRoomIsBuildService extends Service {
    private static final String TAG = CheckRoomIsBuildService.class.getSimpleName();
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
                sendToReceiver(SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild());
            }
        }, 1, 500);
    }

    private void sendToReceiver(boolean isRoomBuild) {
        Intent intent = new Intent();
        intent.setAction(CheckRoomIsBuildReceiver.TAG);
        intent.putExtra("is_room_build", isRoomBuild);
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
