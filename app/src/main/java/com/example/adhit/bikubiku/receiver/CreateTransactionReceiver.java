package com.example.adhit.bikubiku.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CreateTransactionReceiver extends BroadcastReceiver {

    public static final String TAG = CreateTransactionReceiver.class.getSimpleName();
    private final PeriodicCheckTransactionProcess mListener;

    public CreateTransactionReceiver(PeriodicCheckTransactionProcess listener) {
        mListener = listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       String idRoom = intent.getStringExtra("id_room_transaction");
        mListener.handleFromReceiver(idRoom);
    }

    public interface PeriodicCheckTransactionProcess {

        void handleFromReceiver(String idRoom);
    }
}
