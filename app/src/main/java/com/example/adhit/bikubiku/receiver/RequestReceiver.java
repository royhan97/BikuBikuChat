package com.example.adhit.bikubiku.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.adhit.bikubiku.data.model.RequestToKabim;

import java.util.ArrayList;
import java.util.List;

public class RequestReceiver extends BroadcastReceiver {

    public static String TAG = RequestReceiver.class.getSimpleName();
    private ICheckListRequest iCheckListRequest;

    public RequestReceiver(ICheckListRequest iCheckListRequest) {
        this.iCheckListRequest = iCheckListRequest;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        List<RequestToKabim> requestToKabimList = intent.getParcelableArrayListExtra("list_request");

        iCheckListRequest.onHandleListRequest(requestToKabimList);
    }

    public interface ICheckListRequest {

        void onHandleListRequest(List<RequestToKabim> requestToKabimList);
    }
}
