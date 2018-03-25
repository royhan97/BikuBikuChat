package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.SakuBikuMenu;

import java.util.List;

/**
 * Created by adhit on 03/01/2018.
 */

public class SakuBikuMenuAdapter extends RecyclerView.Adapter<SakuBikuMenuAdapter.SakuBikuMenuAdapterViewHolder> {
    private Context context;
    private List<SakuBikuMenu> sakuBikuMenuList;
    private OnDetailListener onDetailListener;

    public SakuBikuMenuAdapter(Context context){
        this.context = context;
    }

    public void setData(List<SakuBikuMenu> SakuBikuMenuList){
        this.sakuBikuMenuList = SakuBikuMenuList;
        notifyDataSetChanged();
    }

    @Override
    public SakuBikuMenuAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_sakubiku, parent, false);
        return new SakuBikuMenuAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SakuBikuMenuAdapterViewHolder holder, int position) {
        final SakuBikuMenu sakuBiku = sakuBikuMenuList.get(position);
        holder.tvNameMenu.setText(sakuBiku.getNamaMenu());
        holder.imgMenu.setImageResource(sakuBiku.getGambarMenu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDetailListener.onItemDetailClicked(sakuBiku.getNamaMenu());
            }
        });
    }

    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }

    @Override
    public int getItemCount() {
        if(sakuBikuMenuList == null) return 0;
        return sakuBikuMenuList.size();
    }

    public class SakuBikuMenuAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameMenu;
        ImageView imgMenu;

        Typeface fontAwesomeFont = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

        public SakuBikuMenuAdapterViewHolder(View itemView) {
            super(itemView);
            imgMenu = itemView.findViewById(R.id.img_menu);
            tvNameMenu = itemView.findViewById(R.id.tv_name_menu);
        }
    }
    public interface OnDetailListener{
        void onItemDetailClicked(String menu);
    }
}