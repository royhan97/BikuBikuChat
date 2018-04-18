package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.detailakun.personalia.PersonaliaView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonaliaPresenter {

    private PersonaliaView personaliaView;

    public PersonaliaPresenter(PersonaliaView personaliaView){
        this.personaliaView = personaliaView;
    }

    public void showPersonalia(){
        RetrofitClient.getInstance()
                .getApi()
                .showPersonalia()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if(status){
                                personaliaView.onSuccessShowPersonalia(message);
                            }else {
                                personaliaView.onFailedShowPersonalia(message);
                            }
                        }else {
                            personaliaView.onFailedShowPersonalia("Terjadi Kesalahan");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        personaliaView.onFailedShowPersonalia(t.getMessage());
                    }
                });
    }
}
