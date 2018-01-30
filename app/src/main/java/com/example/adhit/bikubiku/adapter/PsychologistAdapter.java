package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.model.Psychologist;
import com.example.adhit.bikubiku.data.model.PsychologistApprove;

import java.util.List;

/**
 * Created by adhit on 07/01/2018.
 */

public class PsychologistAdapter extends RecyclerView.Adapter<PsychologistAdapter.HomeViewHolder> {

    private Context context;
    private List<PsychologistApprove> psychologistApproveList;
    private OnDetailListener onDetailListener;

    public PsychologistAdapter(Context context){
        this.context = context;
    }

    public void setData(List<PsychologistApprove> psychologistApproveList){
        this.psychologistApproveList = psychologistApproveList;
        notifyDataSetChanged();
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_psychologist, parent, false);
        return new HomeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {
        final PsychologistApprove psychologistApprove = psychologistApproveList.get(position);
        holder.tvNamePsychologist.setText(psychologistApprove.getNama());
        holder.tvConsultationPrice.setText(psychologistApprove.getTarif());
        if(SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild()){
            holder.btnStartChat.setClickable(false);
            holder.btnStartChat.setBackgroundResource(R.color.grey_500);
        }else {
            holder.btnStartChat.setClickable(true);
            holder.btnStartChat.setBackgroundResource(R.color.colorGreen400);
            holder.btnStartChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onDetailListener.onItemDetailClicked(psychologistApprove );
                }
            });
        }

    }

    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }

    @Override
    public int getItemCount() {
        if(psychologistApproveList == null) return 0;
        return psychologistApproveList.size();
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
        void onItemDetailClicked(PsychologistApprove  psychologistApprove);
    }
}