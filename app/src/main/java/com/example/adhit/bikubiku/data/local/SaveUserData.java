package com.example.adhit.bikubiku.data.local;

import android.content.Context;
import android.util.Base64;

import com.example.adhit.bikubiku.data.model.Invoice;
import com.example.adhit.bikubiku.data.model.Transaction;
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

    public void saveUserTransaction(Invoice transaction){
        SharedPrefUtil.saveObject(Constant.KEY_TRANSACTION, transaction);
    }
    public Transaction getTransaction(){
        return (Transaction) SharedPrefUtil.getObject(Constant.KEY_TRANSACTION, Transaction.class);
    }
    public void removeTransaction(){
        SharedPrefUtil.remove(Constant.KEY_TRANSACTION);
    }

    public void saveEndTimeOfTransaction(Long time){
        SharedPrefUtil.saveLong(Constant.END_TIME_OF_TRANSACTION, time);
    }

    public long getEndTimeOfTransaction(){
        return SharedPrefUtil.getLong(Constant.END_TIME_OF_TRANSACTION);
    }

    public void removeEndTimeOfTransaction(){
        SharedPrefUtil.remove(Constant.END_TIME_OF_TRANSACTION);
    }

    public void savePsychologist(String name){
        SharedPrefUtil.saveString(Constant.PSYCHOLOGIST_DATA, name);
    }

    public String getPsychologistData(){
        return SharedPrefUtil.getString(Constant.PSYCHOLOGIST_DATA);
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

    public int getPsychologyConsultationRoomChat() {
        return  SharedPrefUtil.getInt(Constant.PSYCHOLOGIST_ROOM_CHAT);
    }

    public void savePsychologyConsultationRoomChat(int idRoom) {
        SharedPrefUtil.saveInt(Constant.PSYCHOLOGIST_ROOM_CHAT, idRoom);
    }

    public void removePsychologyConsultationRoomChat(){
        SharedPrefUtil.remove(Constant.PSYCHOLOGIST_ROOM_CHAT);
    }

    public void setRoomChatPsychologyConsultationIsBuild(boolean roomChatPsychologyConsultationIsBuild) {
       SharedPrefUtil.saveBoolean(Constant.IS_ROOM_CHAT_CONSULTATION_BUILD, roomChatPsychologyConsultationIsBuild);
    }

    public boolean isRoomChatPsychologyConsultationBuild(){
        return  SharedPrefUtil.getBoolean(Constant.IS_ROOM_CHAT_CONSULTATION_BUILD);
    }
}
