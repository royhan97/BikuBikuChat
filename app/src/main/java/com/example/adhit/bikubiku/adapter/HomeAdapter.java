package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Home;

import java.util.List;

/**
 * Created by adhit on 03/01/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context context;
    private List<Home> homeList;

    public HomeAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Home> homeList){
        this.homeList = homeList;
        notifyDataSetChanged();
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_menu, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        final Home home = homeList.get(position);
        holder.tvNameMenu.setText(home.getNamaMenu());
        holder.imgMenu.setImageResource(home.getGambarMenu());
    }

    @Override
    public int getItemCount() {
        if(homeList == null) return 0;
        return homeList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameMenu;
        ImageView imgMenu;

        public HomeViewHolder(View itemView) {
            super(itemView);
            imgMenu = itemView.findViewById(R.id.img_menu);
            tvNameMenu = itemView.findViewById(R.id.tv_name_menu);
        }
    }
}