package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SQLLite;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SaveUserToken;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.login.LoginView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;


import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 03/01/2018.
 */

public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void checkLogin() {
        if (Session.getInstance().isLogin()) loginView.gotoHome();
    }


    public void Login(final Context context, final String username, String password){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .login(username, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonObject userObject = body.get("result").getAsJsonObject();
                                Type type = new TypeToken<User>(){}.getType();
                                User user = new Gson().fromJson(userObject, type);
                                //listGalleryView.showData(carList);
                                SaveUserToken.getInstance().saveUserToken(user.getId(), user.getToken());
                                SaveUserData.getInstance().saveUser(user);
                                Session.getInstance().setLogin(true);
                                loginView.gotoHome();
                                loginView.showMessage("Selamat Datang " + user.getNama());
                            }else{
                                String message = body.get("message").getAsString();
                                loginView.showMessageSnackbar(message);
                            }
                        }else {
                            loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        }
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        ShowAlert.closeProgresDialog();
                    }
                });
    }
}
