package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.home.home.HomeView;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 03/01/2018.
 */

public class HomePresenter {

    private HomeView homeView;
    public HomePresenter(HomeView homeView){
        this.homeView = homeView;
    }

    public void showListHome(){
        ArrayList<Home> homeArrayList = new ArrayList<>();
        homeArrayList.add(new Home(R.drawable.logo, "Library"));
        homeArrayList.add(new Home(R.drawable.logo, "Ruang Belajar"));
        homeArrayList.add(new Home(R.drawable.logo, "Tes Minat"));
        homeArrayList.add(new Home(R.drawable.logo, "Artikel"));
        homeArrayList.add(new Home(R.drawable.logo, "Konsultasi Psikologi"));
        homeArrayList.add(new Home(R.drawable.logo, "History Konsultasi"));
        homeView.showData(homeArrayList);

    }

    public void showSaldo(){
        RetrofitClient.getInstance()
                .getApi()
                .getBalance()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            JsonObject result = body.get("result").getAsJsonObject();
                            String balance = result.get("saldo").getAsString();
                            homeView.showSaldo("Rp "+balance);
                        }else{
                            homeView.showSaldo("Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        homeView.showSaldo("Error");
                    }
                });
    }
}
