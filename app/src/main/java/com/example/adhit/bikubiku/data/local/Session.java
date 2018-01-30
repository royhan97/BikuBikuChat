package com.example.adhit.bikubiku.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by adhit on 03/01/2018.
 */

public class Session {

    private static Session ourInstance;

    private Session() {
    }

    public static Session getInstance() {
        if (ourInstance == null) ourInstance = new Session();
        return ourInstance;
    }

    public boolean isLogin() {
        return SharedPrefUtil.getBoolean(Constant.IS_LOGIN);
    }

    public boolean isBuildRoomRuangBelajar(){
        return SharedPrefUtil.getBoolean(Constant.IS_ROOM_BUILD_RUANG_BELAJAR);
    }

    public void setLogin(boolean isLogin) {
        SharedPrefUtil.saveBoolean(Constant.IS_LOGIN, isLogin);
    }

    public void setBuildRoomRuangBelajar (boolean isBuildRoomRuangBelajar){
        SharedPrefUtil.saveBoolean(Constant.IS_ROOM_BUILD_RUANG_BELAJAR, isBuildRoomRuangBelajar);
    }

    public void setKabimLogin(boolean isKabimLogin) {
        SharedPrefUtil.saveBoolean(Constant.IS_LOGIN_KABIM, isKabimLogin);
    }
}
