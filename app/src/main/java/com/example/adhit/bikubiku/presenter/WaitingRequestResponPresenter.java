package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.data.model.RequestToKabim;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.waitingrequestresponse.WaitingRequestResponView;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 1/24/2018.
 */

public class WaitingRequestResponPresenter {

    private WaitingRequestResponView waitingRequestResponView;

    public WaitingRequestResponPresenter(WaitingRequestResponView waitingRequestResponView) {
        this.waitingRequestResponView = waitingRequestResponView;
    }

    public void getDetailTrx(String layanan, String invoice){
        RetrofitClient.getInstance()
                .getApi()
                .detailTrx(layanan,invoice)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if (status){
                                JsonObject trx = body.get("result").getAsJsonObject();
                                int statusTrx = trx.get("status_trx").getAsInt();
                                int idRoom = -1;
                                if (!trx.get("id_room").isJsonNull()){
                                    idRoom = trx.get("id_room").getAsInt();
                                }
                                waitingRequestResponView.getStatusAndIdRoom(statusTrx,idRoom);
                            }
                            else {
                                waitingRequestResponView.showError(message);
                            }

                        }
                        else {
                            waitingRequestResponView.showError("tidak dapat mengambil detail transaksi");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                        waitingRequestResponView.showError("gagal mengambil detail transaksi");
                    }
                });
    }
}
