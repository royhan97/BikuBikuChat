package com.example.adhit.bikubiku.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by roy on 12/10/2017.
 */

public class DetailKabim implements Parcelable {

    private String description;
    private ArrayList<ReviewKabim> reviews;

    public DetailKabim(String description, ArrayList<ReviewKabim> reviews) {
        this.description = description;
        this.reviews = reviews;
    }

    protected DetailKabim(Parcel in) {
        this.description = in.readString();
        this.reviews = in.readArrayList(null);
    }

    public static final Creator<DetailKabim> CREATOR = new Creator<DetailKabim>() {
        @Override
        public DetailKabim createFromParcel(Parcel in) {
            return new DetailKabim(in);
        }

        @Override
        public DetailKabim[] newArray(int size) {
            return new DetailKabim[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeList(reviews);
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<ReviewKabim> getReviews() {
        return reviews;
    }
}
