package com.example.adhit.bikubiku.data.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by adhit on 03/01/2018.
 */

public interface Api {
    @POST("user/register")
    @FormUrlEncoded
    Call<JsonObject> register(@Field("nama") String name,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("email") String email,
                              @Field("tujuan") String aim);

    @POST("user/login")
    @FormUrlEncoded
    Call<JsonObject> login(@Field("username") String username,
                           @Field("password") String password);

    @POST("biquers/alamat")
    @FormUrlEncoded
    Call<JsonObject> changeAddress(@Field("alamat") String address);

    @POST("biquers/profil")
    @FormUrlEncoded
    Call<JsonObject> changeAccount(@Field("nama") String name,
                                   @Field("username") String username,
                                   @Field("password") String password,
                                   @Field("email") String email,
                                   @Field("tujuan") String aim);

    @POST("biquers/ubahpassword")
    @FormUrlEncoded
    Call<JsonObject> changePassword(@Field("password_lama") String oldPassword,
                                   @Field("password_baru") String newPassword,
                                   @Field("password_baru_kedua") String newPasswordConfirm);


}
