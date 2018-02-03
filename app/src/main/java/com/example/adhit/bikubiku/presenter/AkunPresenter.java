package com.example.adhit.bikubiku.presenter;

import android.app.Activity;
import android.content.Intent;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SaveUserToken;
import com.example.adhit.bikubiku.data.local.SaveUserTrxPR;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.ui.home.akun.AkunView;
import com.example.adhit.bikubiku.ui.home.home.HomeView;
import com.example.adhit.bikubiku.ui.login.LoginActivity;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.qiscus.sdk.Qiscus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by adhit on 04/01/2018.
 */

public class AkunPresenter {
    private AkunView akunView;
    public AkunPresenter(AkunView akunView){
        this.akunView = akunView;
    }

    public void showListAkunMenu(){
        ArrayList<Home> akunArrayList = new ArrayList<>();
        akunArrayList.add(new Home(R.drawable.logo, "Profil"));
        akunArrayList.add(new Home(R.drawable.logo, "Personalia"));
        akunArrayList.add(new Home(R.drawable.logo, "My Library"));
        akunArrayList.add(new Home(R.drawable.logo, "Pencapaian"));
        akunArrayList.add(new Home(R.drawable.logo, "Panel Kabim"));
        akunArrayList.add(new Home(R.drawable.logo, "Log Out"));
        akunView.showData(akunArrayList);

    }

    public void showDataUser(){
        User user = SaveUserData.getInstance().getUser();
        akunView.showUserData(user);
    }

    public void userLogOut(){
        if(SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild() || Session.getInstance().isBuildRoomRuangBelajar()){
                akunView.onFailureLogOut();
        }else{
            Qiscus.clearUser();
            Session.getInstance().setLogin(false);
            Session.getInstance().setKabimLogin(false);
            Session.getInstance().setBuildRoomRuangBelajar(false);
            SaveUserToken.getInstance().removeUserToken();
            SaveUserData.getInstance().removeUser();
            SaveUserTrxPR.getInstance().removeTrx();
            SharedPrefUtil.remove(Constant.SALDO_USER);
            akunView.onSuccessLogOut();
        }

    }


}
