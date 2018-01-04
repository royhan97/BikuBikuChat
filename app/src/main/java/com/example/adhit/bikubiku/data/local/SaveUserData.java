package com.example.adhit.bikubiku.data.local;

import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

/**
 * Created by adhit on 04/01/2018.
 */

public class SaveUserData {
    private static SaveUserData ourInstance;

    private SaveUserData() {
    }

    public static SaveUserData getInstance() {
        if (ourInstance == null) ourInstance = new SaveUserData();
        return ourInstance;
    }

    public User getUser() {
        return (User) SharedPrefUtil.getObject(Constant.KEY_USER, User.class);
    }

    public void saveUser(User user) {
        SharedPrefUtil.saveObject(Constant.KEY_USER, user);
    }

    public void removeUser(){
        SharedPrefUtil.remove(Constant.KEY_USER);
    }
}
