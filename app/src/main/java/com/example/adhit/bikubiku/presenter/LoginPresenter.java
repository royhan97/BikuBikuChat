package com.example.adhit.bikubiku.presenter;

import android.content.Context;
import android.util.Log;

import com.example.adhit.bikubiku.R;
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
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.util.QiscusErrorLogger;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

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
//                                loginSDK(user);
                                loginOrNotQiscus(context,user, user.getId(), user.getToken(), user.getNama(), Integer.parseInt(user.getStatusKabim()));
                                ShowAlert.closeProgresDialog();
                            }else{
                                String message = body.get("message").getAsString();
                                loginView.showMessageSnackbar(message);
                                ShowAlert.closeProgresDialog();
                            }
                        }else {
                            loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                            ShowAlert.closeProgresDialog();
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        System.out.println(t.getMessage());
                        loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        ShowAlert.closeProgresDialog();
                    }
                });
    }

    public void loginOrNotQiscus(Context context, User user, String id, String key, String userName, int statusKabim){
//        if (Qiscus.hasSetupUser()){
//            Qiscus.clearUser();
//            ShowAlert.showProgresDialog(context);
//        }
//        else {
            Qiscus.setUser(user.getId()+"b", key)
                    .withUsername(userName)
                    .save()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(qiscusAccount -> {
                        Log.i("MainActivity", "Login with account: " + qiscusAccount);
                        if (statusKabim == 1){
                            Session.getInstance().setKabimLogin(true);
                        }
                        SaveUserToken.getInstance().saveUserToken(id, key);
                        SaveUserData.getInstance().saveUser(user);
                        Session.getInstance().setLogin(true);

                       // ShowAlert.showToast(context, "status kabim : "+statusKabim);

                        loginView.gotoHome();
                        loginView.showMessage("Selamat Datang " + user.getNama());
                    }, throwable -> {
                        QiscusErrorLogger.print(throwable);
                        ShowAlert.showToast(context,QiscusErrorLogger.getMessage(throwable));
                    });
            ShowAlert.closeProgresDialog();
//        }
    }

//    public void loginSDK(User user){
//        Qiscus.setUser(user.getId(), user.getToken())
//                .withUsername(user.getNama())
//                .save()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(qiscusAccount -> {
//                    SaveUserToken.getInstance().saveUserToken(user.getId(), user.getToken());
//                    SaveUserData.getInstance().saveUser(user);
//                    Session.getInstance().setLogin(true);
//                    loginView.showMessage("Selamat Datang " + user.getNama());
//                    loginView.gotoHome();
//                    ShowAlert.closeProgresDialog();
//                }, throwable -> {
//                    if (throwable instanceof HttpException) { //Error response from server
//                        HttpException e = (HttpException) throwable;
//                        try {
//                            String errorMessage = e.response().errorBody().string();
//                            Log.e(TAG, errorMessage);
//                            loginView.showMessageSnackbar(errorMessage);
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    } else if (throwable instanceof IOException) { //Error from network
//                        loginView.showMessageSnackbar("Can not connect to qiscus server!");
//                    } else { //Unknown error
//                        loginView.showMessageSnackbar("Unexpected error!");
//                    }
//                    ShowAlert.closeProgresDialog();
//                });
//        ShowAlert.closeProgresDialog();
//    }
}
