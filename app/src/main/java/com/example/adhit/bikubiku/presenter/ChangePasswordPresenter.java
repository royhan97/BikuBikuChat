package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.detailakun.profil.changepassword.ChangePasswordView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.JsonObject;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 06/01/2018.
 */

public class ChangePasswordPresenter {
    private ChangePasswordView changePasswordView;
    public ChangePasswordPresenter (ChangePasswordView changePasswordView){
        this.changePasswordView = changePasswordView;
    }

    public void changePassword(String oldPassword, String newPassword, String newPasswordConfirm){
        RetrofitClient.getInstance()
                .getApi()
                .changePassword(stringtoSHA1(oldPassword), stringtoSHA1(newPassword), stringtoSHA1(newPasswordConfirm))
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            String message = body.get("message").getAsString();
                            changePasswordView.onSuccessChangePassword(message);
                        }else {
                            changePasswordView.onFailedChangePassword();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        changePasswordView.onFailedChangePassword();
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
