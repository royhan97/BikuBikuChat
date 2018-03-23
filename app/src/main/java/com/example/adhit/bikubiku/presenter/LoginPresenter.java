package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.login.LoginView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.util.QiscusErrorLogger;

import java.lang.reflect.Type;
import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        RetrofitClient.getInstance()
                .getApi()
                .login(username, stringtoSHA1(password))
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
                                if(user.getStatusAkun().equals("0")){
                                    loginView.onFailedLogin("0");
                                }else{
                                    qiscusLogin(context,user, user.getId(), user.getToken(), user.getNama(), Integer.parseInt(user.getStatusKabim()));
                                }
                            }else{
                                String message = body.get("message").getAsString();
                                loginView.onFailedLogin(message);
                            }
                        }else {
                            loginView.onFailedLogin(context.getResources().getString(R.string.text_login_failed));
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loginView.onFailedLogin(t.toString());
                    }
                });
    }

    public void qiscusLogin(Context context, User user, String id, String key, String userName, int statusKabim){
            Qiscus.setUser(user.getId()+"b", key)
                    .withUsername(userName)
                    .save()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(qiscusAccount -> {
                        if (statusKabim == 1){
                            Session.getInstance().setKabimLogin(true);
                        }
                        SaveUserData.getInstance().saveUserToken(id, key);
                        SaveUserData.getInstance().saveUser(user);
                        Session.getInstance().setLogin(true);
                        loginView.gotoHome();
                        loginView.onSuccessLogin("Selamat Datang " + user.getNama());
                    }, throwable -> {
                        loginView.onFailedLogin(QiscusErrorLogger.getMessage(throwable));
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
