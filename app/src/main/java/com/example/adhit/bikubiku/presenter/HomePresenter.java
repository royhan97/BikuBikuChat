package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.home.home.HomeView;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 03/01/2018.
 */

public class HomePresenter {

    private HomeView homeView;
    private Context context;
    public HomePresenter(Context context, HomeView homeView){
        this.homeView = homeView;
        this.context = context;
    }

    public void showListHome(){
        ArrayList<Home> homeArrayList = new ArrayList<>();
        //homeArrayList.add(new Home(context.getString(R.string.fa_archieve), "Library"));
        homeArrayList.add(new Home(context.getString(R.string.fa_graduation), "Ruang Belajar"));
        homeArrayList.add(new Home(context.getString(R.string.fa_check), "Tes Minat"));
        homeArrayList.add(new Home(context.getString(R.string.fa_text_o), "Artikel"));
        homeArrayList.add(new Home(context.getString(R.string.fa_we_chat), "Konsultasi Psikologi"));
        homeArrayList.add(new Home(context.getString(R.string.fa_history), "History Konsultasi"));
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
                            homeView.onSuccessShowSaldo("Rp "+balance);
                        }else{
                            homeView.onFailedShowSaldo("Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        homeView.onFailedShowSaldo("Error");
                    }
                });
    }

    public void showName() {
        String name[] = SaveUserData.getInstance().getUser().getNama().split(" ");
        homeView.onSuccessShowName(name[0]);

    }
}
