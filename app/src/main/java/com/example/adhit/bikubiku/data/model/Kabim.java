package com.example.adhit.bikubiku.data.model;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by adhit on 29/12/2017.
 */

public class Kabim extends ExpandableGroup<DetailKabim>{

    private String avatarUrlKabim;
    private String emailKabim;
    private int idKabim;
    private String nameKabim;
    private String userNameKabim;

    public Kabim(List<DetailKabim> items, String avatarUrlKabim, String emailKabim, int idKabim, String nameKabim, String userNameKabim) {
        super(nameKabim,items);
        this.avatarUrlKabim = avatarUrlKabim;
        this.emailKabim = emailKabim;
        this.idKabim = idKabim;
        this.nameKabim = nameKabim;
        this.userNameKabim = userNameKabim;
    }

    protected Kabim(Parcel in) {
        super(in);
    }

    public String getAvatarUrlKabim() {
        return avatarUrlKabim;
    }

    public String getEmailKabim() {
        return emailKabim;
    }

    public int getIdKabim() {
        return idKabim;
    }

    public String getNameKabim() {
        return nameKabim;
    }

    public String getUserNameKabim() {
        return userNameKabim;
    }
}
