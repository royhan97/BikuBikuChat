package com.example.adhit.bikubiku.service;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserTrxPR;
import com.example.adhit.bikubiku.data.model.RequestToKabim;
import com.example.adhit.bikubiku.presenter.ListRequestToKabimPresenter;
import com.example.adhit.bikubiku.presenter.WaitingRequestResponPresenter;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.receiver.RequestReceiver;
import com.example.adhit.bikubiku.ui.listRequestToKabim.ListRequestToKabimView;
import com.example.adhit.bikubiku.ui.waitingrequestresponse.WaitingRequestResponView;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roy on 1/22/2018.
 */

public class RequestKabimService extends GcmTaskService implements ListRequestToKabimView, WaitingRequestResponView {

    public static String TAG_LIST_REQUEST = "check_list_request_for_kabim";
    public static String TAG_DETAIL_TRX = "check_detail_trx";
    private ListRequestToKabimPresenter listRequestToKabimPresenter = new ListRequestToKabimPresenter(this);
    private WaitingRequestResponPresenter waitingRequestResponPresenter = new WaitingRequestResponPresenter(this);


    @Override
    public int onRunTask(TaskParams taskParams) {
        if (taskParams.getTag().equals(TAG_LIST_REQUEST)){
            listRequestToKabimPresenter.getListRequest(getApplicationContext());
        }
        else if (taskParams.getTag().equals(TAG_DETAIL_TRX)){
            if (SaveUserTrxPR.getInstance().getTrx() != null){
                waitingRequestResponPresenter.getDetailTrx(SaveUserTrxPR.getInstance().getTrx().getLayanan(), SaveUserTrxPR.getInstance().getTrx().getInvoice());
            }
        }
        System.out.println("task run");
        return GcmNetworkManager.RESULT_SUCCESS;
    }

    @Override
    public void setListRequestToKabim(List<RequestToKabim> requestToKabimList) {
        Intent intent = new Intent();
        intent.setAction(RequestReceiver.TAG);
        intent.putParcelableArrayListExtra("list_request", (ArrayList<? extends Parcelable>) requestToKabimList);
        sendBroadcast(intent);
        Log.d(TAG_LIST_REQUEST,"send to request receiver");
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void getStatusAndIdRoom(int statusTrx, int idRoom) {

    }

}
