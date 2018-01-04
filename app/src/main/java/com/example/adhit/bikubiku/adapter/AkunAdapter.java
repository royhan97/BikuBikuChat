package com.example.adhit.bikubiku.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SaveUserToken;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.ui.login.LoginActivity;

import java.util.List;

/**
 * Created by adhit on 04/01/2018.
 */

public class AkunAdapter extends RecyclerView.Adapter<AkunAdapter.HomeViewHolder> {
    private Context context;
    private List<Home> homeList;
    private OnCardViewClickListener onCardViewClickListener;

    public AkunAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Home> homeList){
        this.homeList = homeList;
        notifyDataSetChanged();
    }

    @Override
    public AkunAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_akun_menu, parent, false);
        return new AkunAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AkunAdapter.HomeViewHolder holder, int position) {
        final Home home = homeList.get(position);
        holder.tvNameMenu.setText(home.getNamaMenu());
        holder.imgMenu.setImageResource(home.getGambarMenu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardViewClickListener.onMenuClicked(home.getNamaMenu());
                if(home.getNamaMenu().equals("Log Out")){
                    Session.getInstance().setLogin(false);
                    SaveUserData.getInstance().removeUser();
                    SaveUserToken.getInstance().removeUserToken();
                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((Activity) context).finish();
                }
            }
        });
    }


    public void setOnClickDetailListener(OnCardViewClickListener onCardViewClickListener){
        this.onCardViewClickListener = onCardViewClickListener;

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

    public  interface OnCardViewClickListener{
        void onMenuClicked(String string);
    }
}