package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.ReviewKabim;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;

/**
 * Created by roy on 12/10/2017.
 */

public class DetailViewHolder extends ChildViewHolder {

    private TextView deskripsi;
    private RecyclerView rvReviews;
    private Context context;

    public DetailViewHolder(Context context, View itemView) {
        super(itemView);
        this.context =context;
        deskripsi = (TextView)itemView.findViewById(R.id.deskripsiEvent);
        rvReviews = (RecyclerView) itemView.findViewById(R.id.listReview);
    }

    public void setContentDeatail (String description, ArrayList<ReviewKabim> reviews){
        deskripsi.setText(description);

        if (reviews.size() == 0){
            reviews.add(new ReviewKabim("0", "", ""));
        }
        ListKabimAdapter listProductAdapter = new ListKabimAdapter(context, reviews);
        rvReviews.setHasFixedSize(true);
        rvReviews.setLayoutManager(new LinearLayoutManager(context));
        rvReviews.setAdapter(listProductAdapter);

    }
}
