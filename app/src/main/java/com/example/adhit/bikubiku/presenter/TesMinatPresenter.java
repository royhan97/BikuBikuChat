package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;
import com.example.adhit.bikubiku.data.model.TesMinat;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.ui.tesminat.TesMinatView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TesMinatPresenter {

    private TesMinatView tesMinatView;

    public TesMinatPresenter(TesMinatView tesMinatView){
        this.tesMinatView = tesMinatView;
    }

    public void showTesMinatQuestion(){
        RetrofitClient.getInstance()
                .getApi()
                .showTesMinatQuestion()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if(status){
                                JsonArray tesMinatQuestionArray = body.get("result").getAsJsonArray();
                                Type type = new TypeToken<List<TesMinat>>(){}.getType();
                                List<TesMinat> tesMinatList =  new Gson().fromJson(tesMinatQuestionArray, type);
                                tesMinatView.onSuccessShowTesMinatQuestion(tesMinatList);
                            }else {
                                tesMinatView.onFailedShowTesMinatQuestion(message, true);
                            }
                        }else{
                            tesMinatView.onFailedShowTesMinatQuestion("Terjadi Kesalahan", false);
                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        tesMinatView.onFailedShowTesMinatQuestion(t.getMessage(), false);
                    }
                });
    }

    public void resetTesMinat(){
        RetrofitClient.getInstance()
                .getApi()
                .resetTesMinat()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if(status){
                                tesMinatView.onSuccessResetTesMinat();
                            }else {
                                tesMinatView.onFailedResetTesMinat(message);
                            }
                        }else {
                            tesMinatView.onFailedResetTesMinat("Terjadi Kesalahan");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        tesMinatView.onFailedResetTesMinat(t.getMessage());
                    }
                });
    }

    public void submitTesMinat(HashMap<String, String> hashMap){
        Map<String, String> mp;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mp = new TreeMap<>(
                    Comparator.comparingInt(key -> Integer.parseInt(key)));
            mp.putAll(hashMap);
        }else {
            mp = new TreeMap<>(new Comparator<String>(){
                @Override
                public int compare(String key1, String key2) {
                    //parse only text after first letter
                    int k1 = Integer.parseInt(key1.substring(1));
                    int k2 = Integer.parseInt(key2.substring(1));
                    return Integer.compare(k1, k2);
                }
            });
            mp.putAll(hashMap);
        }
//
        JSONObject json = new JSONObject(mp);

        RetrofitClient.getInstance()
                .getApi()
                .submitTesMinat(json)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("message").getAsString();
                            if(status){
                                tesMinatView.onSuccessSubmitTesMinat(message);
                            }else {
                                tesMinatView.onFailedSubmitTesMinat(message);
                            }
                        }else {
                            tesMinatView.onFailedSubmitTesMinat("Terjadi Kesalahan");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        tesMinatView.onFailedSubmitTesMinat(t.getMessage());
                    }
                });

    }
}
