package com.example.adhit.bikubiku.presenter;

import com.example.adhit.bikubiku.data.model.DetailKabim;
import com.example.adhit.bikubiku.data.model.Kabim;
import com.example.adhit.bikubiku.data.model.ReviewKabim;
import com.example.adhit.bikubiku.ui.listKabim.ListKabimFragmentView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

import java.util.ArrayList;

/**
 * Created by roy on 1/5/2018.
 */

public class ListKabimFragmentPresenter {


    private ArrayList<Kabim> listKabim;
    private ArrayList<DetailKabim> listDetailKabim;
    private ArrayList<ReviewKabim> listReview;
    private ListKabimFragmentView listKabimView;

    public ListKabimFragmentPresenter(ListKabimFragmentView listKabimView) {
        this.listKabimView = listKabimView;
    }

    public void getListKabim(){
        listDetailKabim = new ArrayList<DetailKabim>();
        listKabim = new ArrayList<Kabim>();
        listReview = new ArrayList<ReviewKabim>();

        listReview.add(new ReviewKabim("4","Good Kabim", "John"));
        listReview.add(new ReviewKabim("5","Excellent Kabim", "Boy"));

        listDetailKabim.add(new DetailKabim("Matematika",listReview));

        listKabim.add(new Kabim(listDetailKabim, "https://www.shareicon.net/data/2016/08/05/806962_user_512x512.png", "roy@gmail.com", 16, "roihan", "roihan"));

        listKabimView.showData(listKabim);
    }

    public void continueLastChat(){
        listKabimView.toRecentRoomChat(SharedPrefUtil.getInt(Constant.ROOM_ID));
    }
}
