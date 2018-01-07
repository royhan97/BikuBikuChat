package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.detailakun.profil.accountsettings.AccountSettingsView;
import com.example.adhit.bikubiku.ui.detailakun.profil.address.AddressView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 06/01/2018.
 */

public class AccountSettingsPresenter {
    private AccountSettingsView accountSettingsView;

    public AccountSettingsPresenter(AccountSettingsView accountSettingsView){
        this.accountSettingsView = accountSettingsView;
    }

    public void getDataAccount(){
        accountSettingsView.getDataAccount(SaveUserData.getInstance().getUser());
    }

    public void changeDataAccount(final Context context, final String name, final String username, String password, final String email, String aim){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .changeAccount(name, username, password, email, aim)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body
                                    .get("status").getAsBoolean();
                            if(status){
                                String message = body.get("message").getAsString();
                                accountSettingsView.showMessage(message);
                                User user = SaveUserData.getInstance().getUser();
                                user.setNama(name);
                                user.setUsername(username);
                                user.setEmail(email);
                                SaveUserData.getInstance().saveUser(user);
                            }else{
                                String message = body.get("message").getAsString();
                                accountSettingsView.showMessage(message);
                            }
                        }else {
                            accountSettingsView.showMessage(context.getResources().getString(R.string.text_changed_failed));
                        }
                        ShowAlert.closeProgresDialog();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                        accountSettingsView.showMessage(context.getResources().getString(R.string.text_changed_failed));
                        ShowAlert.closeProgresDialog();
                    }
                });
    }
}
