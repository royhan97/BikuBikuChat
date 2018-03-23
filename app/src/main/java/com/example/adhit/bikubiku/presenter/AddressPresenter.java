package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.detailakun.profil.address.AddressView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 05/01/2018.
 */

public class AddressPresenter {

    private AddressView addressView;

    public AddressPresenter(AddressView addressView){
        this.addressView = addressView;
    }

    public void getDataAddress(){
        addressView.getDataAddress(SaveUserData.getInstance().getUser().getAlamat());
    }

    public void changeDataAddress(final Context context, final String alamat){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .changeAddress(alamat)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                             if(response.isSuccessful()){
                                 JsonObject body = response.body();
                                 boolean status = body
                                         .get("status").getAsBoolean();
                                 if(status){
                                     String message = body.get("message").getAsString();
                                     addressView.showMessage(message);
                                     User user = SaveUserData.getInstance().getUser();
                                     user.setAlamat(alamat);
                                     SaveUserData.getInstance().saveUser(user);
                                 }else{
                                     String message = body.get("message").getAsString();
                                     addressView.showMessage(message);
                                 }
                             }else {
                                 addressView.showMessage(context.getResources().getString(R.string.text_changed_failed));
                             }
                             ShowAlert.closeProgresDialog();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                        addressView.showMessage(context.getResources().getString(R.string.text_changed_failed));
                        ShowAlert.closeProgresDialog();
                    }
                });
    }


}
