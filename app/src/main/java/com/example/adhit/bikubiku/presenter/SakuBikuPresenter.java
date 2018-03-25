package com.example.adhit.bikubiku.presenter;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.AccountBank;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.model.SakuBikuMenu;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.home.home.HomeView;
import com.example.adhit.bikubiku.ui.sakubiku.SakuBikuView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 3/24/2018.
 */

public class SakuBikuPresenter {
    private SakuBikuView sakuBikuView;
    public SakuBikuPresenter( SakuBikuView sakuBikuView){
        this.sakuBikuView = sakuBikuView;
    }

    public void showListSakuBikuMenu(){
        ArrayList<SakuBikuMenu> sakuBikuMenuArrayList = new ArrayList<>();
        sakuBikuMenuArrayList.add(new SakuBikuMenu(R.drawable.ic_account_balance_wallet_black_24dp, "Top Up"));
        sakuBikuMenuArrayList.add(new SakuBikuMenu(R.drawable.ic_attach_money_black_24dp, "Tarik Dana"));
        sakuBikuMenuArrayList.add(new SakuBikuMenu(R.drawable.ic_account_balance_wallet_black_24dp, "Tambah Rekening"));
        sakuBikuView.showData(sakuBikuMenuArrayList);
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
                            String topup, cashout;
                            topup = result.get("masuk_tertahan").toString();
                            cashout = result.get("keluar_tertahan").toString();
                            if(!topup.equals("null")){
                                topup = "Rp "+result.get("masuk_tertahan").getAsString();
                            }else{
                                topup = "Tidak Ada";
                            }
                            if(!cashout.equals("null")){
                                cashout = "Rp "+result.get("keluar_tertahan").getAsString();
                            }else{
                                cashout = "Tidak Ada";
                            }
                            sakuBikuView.onSuccessShowSaldo(balance, topup, cashout);
                        }else{
                            sakuBikuView.onFailedShowSaldo("Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        sakuBikuView.onFailedShowSaldo("Error");
                    }
                });
    }

    public void topupBalacne(String aimBank, String balance){
        RetrofitClient.getInstance()
                .getApi()
                .topupBalance(aimBank, balance)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                               sakuBikuView.onSuccessTopupBalance();
                            }else{
                                String message = body.get("message").getAsString();
                                sakuBikuView.onFailedTopupBalance(message);
                            }
                        }else{
                            sakuBikuView.onFailedTopupBalance("Top Up Gagal");
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        sakuBikuView.onFailedTopupBalance(t.toString());
                    }
                });
    }

    public void cashoutBalacne(String balance){
        RetrofitClient.getInstance()
                .getApi()
                .cashoutBalance(balance)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                sakuBikuView.onSuccessCashOutBalance();
                            }else{
                                String message = body.get("message").getAsString();
                                sakuBikuView.onFailedCashOutBalance(message);
                            }
                        }else{
                            sakuBikuView.onFailedCashOutBalance("Top Up Gagal");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        sakuBikuView.onFailedCashOutBalance(t.toString());
                    }
                });
    }

    public void accountBankManagement(String bank, String accountName, String accountNumber){
        RetrofitClient.getInstance()
                .getApi()
                .accountBankManagement(bank, accountName, accountNumber)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonObject accountBankObject = body.get("result").getAsJsonObject();
                                Type type = new TypeToken<AccountBank>(){}.getType();
                                AccountBank accountBank = new Gson().fromJson(accountBankObject, type);
                                sakuBikuView.onSuccessAddAccountBank(accountBank);
                            }else{
                                String message = body.get("message").getAsString();
                                sakuBikuView.onFailedAddAccountBank(message);
                            }
                        }else{
                            sakuBikuView.onFailedAddAccountBank("Penambahan Rekening Gagal");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        sakuBikuView.onFailedAddAccountBank(t.toString());
                    }
                });
    }

    public void getAccountBankData(){
        RetrofitClient.getInstance()
                .getApi()
                .getAccountBankData()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonObject accountBankObject = body.get("result").getAsJsonObject();
                                Type type = new TypeToken<AccountBank>(){}.getType();
                                AccountBank accountBank = new Gson().fromJson(accountBankObject, type);
                                sakuBikuView.onSuccessGetAccountBank(accountBank);
                            }else{
                                String message = body.get("message").getAsString();
                                sakuBikuView.onFailedGetAccountBank(message);
                            }
                        }else{
                            sakuBikuView.onFailedGetAccountBank("Pengambilan Data Akun Gagal");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        sakuBikuView.onFailedGetAccountBank(t.toString());
                    }
                });
    }


}
