package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.data.local.SavePsychologyConsultationRoomChat;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.model.Invoice;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.detailpsychologist.TransactionView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 18/01/2018.
 */

public class TransactionPresenter {

    private TransactionView transactionView;
    public TransactionPresenter(TransactionView transactionView){
        this.transactionView =transactionView;
    }

    public void createTransaction(Context context, String service, String duration, String explanation, String idPsychologist){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .createTransaction(service, duration, explanation, idPsychologist)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        System.out.println(response.body());
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            String message = body.get("message").getAsString();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(true);
                                JsonObject transactionObject = body.get("result").getAsJsonObject();
                                Type type = new TypeToken<Invoice>(){}.getType();
                                Invoice invoice = new Gson().fromJson(transactionObject, type);
                                SaveUserData.getInstance().saveUserTransaction(invoice);
                                transactionView.onSuccessMakeTransaction("Berhasil");
                                System.out.println("ID "+invoice.getInvoice());
                            }else{
                                transactionView.onFailure(message);
                            }
                            ShowAlert.closeProgresDialog();
                        }else{
                            transactionView.onFailure("Failed1");
                            ShowAlert.closeProgresDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(call.toString());
                        System.out.println(t.getMessage());
                        transactionView.onFailure("Failed "+ t.getMessage());
                        ShowAlert.closeProgresDialog();
                    }
                });
    }

    public void changeTransacationStatus (String service, String invoice, int idRoom, String status){
        System.out.println("panggilan ganti" + idRoom);
        RetrofitClient.getInstance()
                .getApi()
                .changeTransactionStatus(service, invoice, idRoom, status)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            System.out.println("ganti transaksi " +body);
                            transactionView.onSuccessChangeTransactionStatus("Berhasil");

                        }else{
                            transactionView.onFailure("Failed");
                           // changeTransacationStatus(service,invoice,idRoom,status);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                            transactionView.onFailure("failed");

                    }
                });

    }

    public void saveEndTimeOfTransaction(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateandTime = sdf.format(new Date(System.currentTimeMillis()));

        Date date = null;
        try {
            date = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 2);

        SaveUserData.getInstance().saveEndTimeOfTransaction(calendar.getTimeInMillis());
    }
}

