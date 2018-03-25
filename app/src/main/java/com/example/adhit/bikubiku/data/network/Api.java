package com.example.adhit.bikubiku.data.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by adhit on 03/01/2018.
 */

public interface Api {

    //qiscus
    @POST("/api/v2/rest/create_room")
    @FormUrlEncoded
    Call<JsonObject> createChatRoomSDK(@Field("name") String roomName,
                                       @Field("creator") String creator,
                                       @Field("participants[]") ArrayList<String> participants);

    @GET("/api/v2/rest/get_user_rooms")
    Call<JsonObject> getChatRoomHistory(@Query("user_email") String userEmail,
                                        @Query("show_participants") boolean showParticipants);

    @POST("/api/v2/rest/post_comment")
    @FormUrlEncoded
    Call<JsonObject> sendMessage(@Field("sender_email") String senderEmail,
                                 @Field("room_id") String roomId,
                                 @Field("message") String message,
                                 @Field("payload") JSONObject payload,
                                 @Field("type") String type);
    @GET("api/v2/rest/user_profile")
    Call<JsonObject> getUserProfile(@Query("user_email") String idUser);


    //bikubiku
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
    Call<JsonObject> changeDataAccount(@Field("nama") String name,
                                   @Field("username") String username,
                                   @Field("email") String email,
                                   @Field("tujuan") String aim,
                                       @Field("wa") String wa,
                                       @Field("id_line") String idLine,
                                       @Field("bio") String bio);

    @POST("biquers/ubahpassword")
    @FormUrlEncoded
    Call<JsonObject> changePassword(@Field("password_lama") String oldPassword,
                                   @Field("password_baru") String newPassword,
                                   @Field("password_baru_kedua") String newPasswordConfirm);

    @GET("biquers/listpsikolog")
    Call<JsonObject> getListPsychologist();

    @GET("saku/saldo")
    Call<JsonObject> getBalance();

    @POST("saku/topup")
    @FormUrlEncoded
    Call<JsonObject> topupBalance(@Field("tujuan") String aimBank, @Field("nominal") String balance);

    @POST("saku/aturrekening")
    @FormUrlEncoded
    Call<JsonObject> accountBankManagement(@Field("bank") String bank,
                                    @Field("atas_nama") String accountName,
                                    @Field("no_rekening") String accountNumber);

    @GET("saku/rekening")
    Call<JsonObject> getAccountBankData();

    @POST("saku/cashout")
    @FormUrlEncoded
    Call<JsonObject> cashoutBalance(@Field("nominal") String balance);

    @POST("ruangbelajar/createtrx")
    @FormUrlEncoded
    Call<JsonObject> createTransaction(@Field("layanan") String service,
                                       @Field("lama") String duration,
                                       @Field("penjelasan") String explanation,
                                       @Field("id_kabim") String idPsychologist);

    @GET("ruangbelajar/transaksibiquers?layanan=psikologi")
    Call<JsonObject> getAllTransaction();

    @POST("ruangbelajar/updatestatus")
    @FormUrlEncoded
    Call<JsonObject> changeTransactionStatus(@Field("layanan") String layanan,
                                             @Field("invoice") String invoice,
                                             @Field("id_room") int idRoom,
                                             @Field("status") String status);

    @GET("ruangbelajar/detailtrx")
    Call<JsonObject> getDetailTrx(@Query("layanan") String service,
                                  @Query("invoice") String invoice);


    @POST("ruangbelajar/createtrx")
    @FormUrlEncoded
    Call<JsonObject> createTrx(@Field("layanan") String layanan,
                               @Field("mapel") int mapel,
                               @Field("lama") int lama,
                               @Field("jenjang") int jenjang,
                               @Field("penjelasan") String penjelasan);

    @GET("ruangbelajar/transaksikabim")
    Call<JsonObject> transaksiKabim();

    @GET("saku/saldo")
    Call<JsonObject> cekSakuBiku();

    @POST("ruangbelajar/updatestatus")
    @FormUrlEncoded
    Call<JsonObject> updateStatusTrx(@Field("layanan") String layanan,
                                     @Field("invoice") String invoice,
                                     @Field("id_room") int idRoom,
                                     @Field("status") String status);

    @GET("ruangbelajar/detailtrx")
    Call<JsonObject>  detailTrx(@Query("layanan") String layanan,
                                @Query("invoice") String invoice);

}
