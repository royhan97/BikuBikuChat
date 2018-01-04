package com.example.adhit.bikubiku.data.local;

import android.util.Base64;

import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

/**
 * Created by adhit on 04/01/2018.
 */

public class SaveUserToken {
    private static SaveUserToken ourInstance;

    private SaveUserToken() {
    }

    public static SaveUserToken getInstance() {
        if (ourInstance == null) ourInstance = new SaveUserToken();
        return ourInstance;
    }

    public String getUserToken() {
        return SharedPrefUtil.getString(Constant.USER_TOKEN);
    }

    public void saveUserToken(String id, String token) {
        byte[] encodedBytes;
        String authorization;
        authorization = id + ":" + token;
        encodedBytes = Base64.encode(authorization.getBytes(), Base64.NO_WRAP);
        authorization = "Basic " + new String(encodedBytes);
        SharedPrefUtil.saveString(Constant.USER_TOKEN, authorization);
    }

    public void removeUserToken(){
        SharedPrefUtil.remove(Constant.USER_TOKEN);
    }
}
