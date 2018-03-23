package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.register.RegisterView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.security.MessageDigest;

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

    public void register(final Context context, String name, String username, String password, String email, String aim){
        RetrofitClient.getInstance()
                .getApi()
                .register(name, username, stringtoSHA1(password), email, aim)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                registerView.onSuccessRegister(context.getResources().getString(R.string.text_register_success));
                            }else{
                                String message = body.get("message").getAsString();
                                registerView.onFailedRegister(message);
                            }
                        }else {
                            registerView.onFailedRegister(context.getResources().getString(R.string.text_register_failed));
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        registerView.onFailedRegister(context.getResources().getString(R.string.text_register_failed));
                    }
                });
    }

    public static String stringtoSHA1(String clearString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

}
