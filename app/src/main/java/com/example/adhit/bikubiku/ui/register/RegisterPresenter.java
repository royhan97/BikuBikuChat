package com.example.adhit.bikubiku.ui.register;

import android.content.Context;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 03/01/2018.
 */

public class RegisterPresenter {
    private RegisterView registerView;
    public RegisterPresenter(RegisterView registerView){
        this.registerView = registerView;
    }

    public void register( String name, String username, String password, String email, String aim){
        ShowAlert.showProgresDialog(BikuBiku.getContext());
        RetrofitClient.getInstance()
                .getApi()
                .register(name, username, password, email, aim)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body
                                    .get("status").getAsBoolean();
                            if(status){
                                registerView.showMessage(BikuBiku.getContext().getResources().getString(R.string.text_register_success));
                            }else{
                                String message = body.get("message").getAsString();
                                registerView.showMessage(message);
                            }
                        }else {
                            registerView.showMessage(BikuBiku.getContext().getResources().getString(R.string.text_register_failed));
                        }
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        registerView.showMessage(BikuBiku.getContext().getResources().getString(R.string.text_register_failed));
                        System.out.println(t.getMessage());
                        ShowAlert.closeProgresDialog();
                    }
                });
    }

}
