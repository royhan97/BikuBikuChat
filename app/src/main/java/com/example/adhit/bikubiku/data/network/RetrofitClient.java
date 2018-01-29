package com.example.adhit.bikubiku.data.network;

import android.util.Log;

import com.example.adhit.bikubiku.data.local.SaveUserToken;
import com.example.adhit.bikubiku.util.Constant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by adhit on 03/01/2018.
 */

public class RetrofitClient {
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){

    }

    public static RetrofitClient getInstance(){
        if(retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return  retrofitClient;
    }

    public Api getApi(){
        return getRetrofit().create(Api.class);
    }

    public Retrofit getRetrofit(){
        OkHttpClient okHttpClient = null;
        if(SaveUserToken.getInstance().getUserToken() == null){
             okHttpClient= new OkHttpClient.Builder()
                     .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build();
        }else {
            okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newRequest;
                    newRequest = request.newBuilder()
                            .addHeader("Authorization", SaveUserToken.getInstance().getUserToken())
                            .build();

                    return chain.proceed(newRequest);
                }
            }).connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();


        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Api getApiQiscus(){
        return getRetrofitQiscus().create(Api.class);
    }
    public Retrofit getRetrofitQiscus(){
        OkHttpClient okHttpClient = null;

        okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest;
                newRequest = request.newBuilder()
                        .addHeader("QISCUS_SDK_SECRET", "3234080bd7dccc7c90a7478d413201d2")
                        .build();
                return chain.proceed(newRequest);
            }
        }).connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_QISCUS)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
