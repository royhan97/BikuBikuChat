package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Psychologist;

import java.util.List;

/**
 * Created by adhit on 07/01/2018.
 */

public class PsychologistAdapter extends RecyclerView.Adapter<PsychologistAdapter.HomeViewHolder> {

    private Context context;
    private List<Psychologist> psychologistList;
    private OnDetailListener onDetailListener;

    public PsychologistAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Psychologist> psychologistList){
        this.psychologistList = psychologistList;
        notifyDataSetChanged();
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_psychologist, parent, false);
        return new HomeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {
        final Psychologist psychologist = psychologistList.get(position);
        holder.tvNamePsychologist.setText(psychologist.getNama());
        holder.tvConsultationPrice.setText(psychologist.getPrice());
        holder.btnStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onDetailListener.onItemDetailClicked(psychologist);
            }
        });

    }

    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }

    @Override
    public int getItemCount() {
        if(psychologistList == null) return 0;
        return psychologistList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamePsychologist;
        TextView tvConsultationPrice;
        Button  btnStartChat;

        public HomeViewHolder(View itemView) {
            super(itemView);
            tvNamePsychologist = itemView.findViewById(R.id.tv_psychologist_name);
            tvConsultationPrice = itemView.findViewById(R.id.tv_price_consultation);
            btnStartChat = itemView.findViewById(R.id.btn_start_chat);

        }
    }
    public interface OnDetailListener{
        void onItemDetailClicked(Psychologist psychologist);
    }
}