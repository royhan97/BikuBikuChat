package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.ChatRoomHistory;
import com.example.adhit.bikubiku.presenter.DateFormatterPresenter;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingHistory.RuangBelajarChattingHistoryView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adhit on 09/01/2018.
 */

public class ChatRoomRuangBelajarHistoryAdapter extends RecyclerView.Adapter<ChatRoomRuangBelajarHistoryAdapter.RoomChatViewHolder> {

    private Context context;
    private List<ChatRoomHistory> chatRoomRuangBelajarHistoryList;
    private DateFormatterPresenter presenter;
    private RuangBelajarChattingHistoryView ruangBelajarChattingHistoryView;

    public ChatRoomRuangBelajarHistoryAdapter(Context context, RuangBelajarChattingHistoryView ruangBelajarChattingHistoryView) {
        this.context = context;
        this.ruangBelajarChattingHistoryView = ruangBelajarChattingHistoryView;
    }

    public ChatRoomRuangBelajarHistoryAdapter() {

    }
    public void setData(List<ChatRoomHistory> items){
        chatRoomRuangBelajarHistoryList = items;
        notifyDataSetChanged();
    }

    @Override
    public RoomChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_ruang_belajar_history, parent, false);
        return new RoomChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomChatViewHolder holder, final int position) {

        for(int i = 0; i< chatRoomRuangBelajarHistoryList.get(position).getParticipants().size(); i++){
            if(chatRoomRuangBelajarHistoryList.get(position).getParticipants().get(i).getEmail().equals(SaveUserData.getInstance().getUser().getId())){
                holder.tvRoomChatName.setText(chatRoomRuangBelajarHistoryList.get(position).getRoomName());
            }
        }
        Picasso.with(context)
                .load(chatRoomRuangBelajarHistoryList.get(position).getRoomAvatarUrl())
                .into(holder.imgAvatarRoomChat);
        presenter = new DateFormatterPresenter();
        holder.tvTimeStamp.setText(presenter.dateFormatter(chatRoomRuangBelajarHistoryList.get(position).getLastCommentTimestamp()));

        holder.itemView.setOnClickListener(v -> {
            ruangBelajarChattingHistoryView.onDetailChatRoomHistory(chatRoomRuangBelajarHistoryList.get(position).getRoomId());
        });
    }


    @Override
    public boolean onFailedToRecycleView(RoomChatViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if(chatRoomRuangBelajarHistoryList == null) return 0;
        return chatRoomRuangBelajarHistoryList.size();
    }

    public class RoomChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoomChatName,tvTimeStamp;
        ImageView imgAvatarRoomChat;


        public RoomChatViewHolder(final View itemView) {
            super(itemView);
            tvRoomChatName = itemView.findViewById(R.id.nameRoom);
            imgAvatarRoomChat = itemView.findViewById(R.id.iconImage);
            tvTimeStamp = itemView.findViewById(R.id.txt_tanggal);
        }
    }
}
