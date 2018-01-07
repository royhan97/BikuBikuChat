package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.detailakun.profil.changepassword.ChangePasswordView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.JsonObject;

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

    public void changePassword(final Context context, String oldPassword, String newPassword, String newPasswordConfirm){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .changePassword(oldPassword, newPassword, newPasswordConfirm)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            String message = body.get("message").getAsString();
                            changePasswordView.showMessage(message);
                        }else {
                            changePasswordView.showMessage(context.getResources().getString(R.string.text_changed_failed));
                        }
                        ShowAlert.closeProgresDialog();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        changePasswordView.showMessage(context.getResources().getString(R.string.text_changed_failed));
                    }
                });
    }

}
