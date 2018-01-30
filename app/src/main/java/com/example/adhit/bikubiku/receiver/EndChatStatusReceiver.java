package com.example.adhit.bikubiku.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EndChatStatusReceiver extends BroadcastReceiver {

    public static final String TAG = EndChatStatusReceiver.class.getSimpleName();
    private final PeriodicCheckCarsReceiverListener mListener;

    public EndChatStatusReceiver(PeriodicCheckCarsReceiverListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isEndChat = intent.getBooleanExtra("status_end_chat", false);
        boolean isRoomBuild = intent.getBooleanExtra("is_room_end", false);
        boolean isEndWait = intent.getBooleanExtra("waiting_end",false);

        mListener.handleFromEndChat(isEndChat);
        mListener.handleFromIsRoomEnd(isRoomBuild);
        mListener.handleFromEndWait(isEndWait);
        System.out.println("is room end : " + isRoomBuild);
    }

    public interface PeriodicCheckCarsReceiverListener {

        void handleFromEndChat(boolean isEndChat);

        void handleFromIsRoomEnd(boolean isRoomBuild);

        void handleFromEndWait(boolean isEndWait);
    }
}
