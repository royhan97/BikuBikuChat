package com.example.adhit.bikubiku.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CheckRoomIsBuildReceiver extends BroadcastReceiver {

    public static final String TAG = CheckRoomIsBuildReceiver.class.getSimpleName();
    private final PeriodicCheckCarsReceiverListener mListener;

    public CheckRoomIsBuildReceiver(PeriodicCheckCarsReceiverListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isRoomBuild = intent.getBooleanExtra("is_room_build", false);

        mListener.handleFromReceiver(isRoomBuild);
    }

    public interface PeriodicCheckCarsReceiverListener {

        void handleFromReceiver(boolean isRoomBuild);
    }
}
