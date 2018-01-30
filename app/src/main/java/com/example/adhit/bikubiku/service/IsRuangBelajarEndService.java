package com.example.adhit.bikubiku.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

import java.util.Timer;
import java.util.TimerTask;

public class IsRuangBelajarEndService extends Service {

    private static final String TAG = IsRuangBelajarEndService.class.getSimpleName();
    private Timer mTimer;

    private volatile HandlerThread mHandlerThread;
    private ServiceHandler mServiceHandler;

    // Define how the handler will process messages
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
            requestDataWithInterval();
        }

        // Define how to handle any incoming messages here
        @Override
        public void handleMessage(Message message) {
            // ...
            // When needed, stop the service with
            // stopSelf();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Service");

        mHandlerThread = new HandlerThread("MyCustomService.HandlerThread");
        mHandlerThread.start();
        // An Android service handler is a handler running on a specific background thread.
        mServiceHandler = new ServiceHandler(mHandlerThread.getLooper());
    }

    private void requestDataWithInterval() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendToReceiver(SharedPrefUtil.getBoolean(Constant.IS_END_CHATTING));
            }
        },2,1000);
    }

    private void sendToReceiver(boolean isRoomEnd) {
        Intent intent = new Intent();
        intent.setAction(EndChatStatusReceiver.TAG);
        intent.putExtra("is_room_end", isRoomEnd);
        sendBroadcast(intent);
        Log.d(TAG,"send to receiver");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mHandlerThread.quit();
        Log.d(TAG, "onDestroy Service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
