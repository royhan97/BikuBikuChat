package com.example.adhit.bikubiku.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adhit on 07/01/2018.
 */

public class Psychologist implements Parcelable {
    private int id;
    private String nama;
    private String price;

    public Psychologist(int id, String nama, String price) {
        this.id = id;
        this.nama = nama;
        this.price = price;
    }

    protected Psychologist(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        price = in.readString();
    }

    public static final Creator<Psychologist> CREATOR = new Creator<Psychologist>() {
        @Override
        public Psychologist createFromParcel(Parcel in) {
            return new Psychologist(in);
        }

        @Override
        public Psychologist[] newArray(int size) {
            return new Psychologist[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
        parcel.writeString(price);
    }
}
