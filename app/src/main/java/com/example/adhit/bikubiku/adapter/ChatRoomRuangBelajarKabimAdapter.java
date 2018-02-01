package com.example.adhit.bikubiku.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
import com.example.adhit.bikubiku.presenter.RuangBelajarChattingKabimPresenter;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingKabim.RuangBelajarChattingKabimView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

/**
 * Created by adhit on 09/01/2018.
 */

public class ChatRoomRuangBelajarKabimAdapter extends RecyclerView.Adapter<ChatRoomRuangBelajarKabimAdapter.RoomChatViewHolder>  {

    private Context context;
    private List<ChatRoomHistory> chatRoomRuangBelajarKabimList;
    private DateFormatterPresenter presenter;
    private RuangBelajarChattingKabimView ruangBelajarChattingKabimView;

    public ChatRoomRuangBelajarKabimAdapter(Context context, RuangBelajarChattingKabimView ruangBelajarChattingKabimView) {
        this.context = context;
        this.ruangBelajarChattingKabimView = ruangBelajarChattingKabimView;
    }

    public ChatRoomRuangBelajarKabimAdapter() {

    }
    public void setData(List<ChatRoomHistory> items){
        chatRoomRuangBelajarKabimList = items;
        notifyDataSetChanged();
    }

    @Override
    public RoomChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_ruang_belajar_kabim, parent, false);
        return new RoomChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomChatViewHolder holder, final int position) {

        for(int i = 0; i< chatRoomRuangBelajarKabimList.get(position).getParticipants().size(); i++){
            if(!chatRoomRuangBelajarKabimList.get(position).getParticipants().get(i).getEmail().equals(SaveUserData.getInstance().getUser().getId())){
                holder.tvRoomChatName.setText(chatRoomRuangBelajarKabimList.get(position).getParticipants().get(i).getUsername());
            }
        }
        Picasso.with(context)
                .load(chatRoomRuangBelajarKabimList.get(position).getRoomAvatarUrl())
                .into(holder.imgAvatarRoomChat);
        holder.tvLastComment.setText(chatRoomRuangBelajarKabimList.get(position).getLastCommentMessage());
        presenter = new DateFormatterPresenter();
        holder.tvTimeStamp.setText(presenter.dateFormatter(chatRoomRuangBelajarKabimList.get(position).getLastCommentTimestamp()));

        holder.itemView.setOnClickListener(v -> {
            ruangBelajarChattingKabimView.onDetailChatRoomKabim(chatRoomRuangBelajarKabimList.get(position).getRoomId());
        });
        if (chatRoomRuangBelajarKabimList.get(position).getUnreadCount() > 0){
            holder.tvUnreadCount.setVisibility(View.VISIBLE);
            holder.tvTimeStamp.setVisibility(View.GONE);
            holder.tvUnreadCount.setText(String.valueOf(chatRoomRuangBelajarKabimList.get(position).getUnreadCount()));
        }
    }


    @Override
    public boolean onFailedToRecycleView(RoomChatViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if(chatRoomRuangBelajarKabimList == null) return 0;
        return chatRoomRuangBelajarKabimList.size();
    }

    public class RoomChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoomChatName,tvTimeStamp, tvLastComment, tvUnreadCount;
        ImageView imgAvatarRoomChat;


        public RoomChatViewHolder(final View itemView) {
            super(itemView);
            tvRoomChatName = itemView.findViewById(R.id.nameRoomBiquers);
            imgAvatarRoomChat = itemView.findViewById(R.id.avatarBiquers);
            tvLastComment = itemView.findViewById(R.id.lastChatBiquers);
            tvTimeStamp = itemView.findViewById(R.id.txt_dateChatBiquers);
            tvUnreadCount = itemView.findViewById(R.id.txt_unreadCount);
        }
    }
}
