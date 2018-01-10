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
import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adhit on 09/01/2018.
 */

public class ChatRoomPsychologyHistoryAdapter extends RecyclerView.Adapter<ChatRoomPsychologyHistoryAdapter.RoomChatViewHolder> {

    private Context context;
    private List<ChatRoomPsychologyHistory> chatRoomPsychologyHistoryList;
    private OnDetailListener onDetailListener;

    public ChatRoomPsychologyHistoryAdapter(Context context) {
        this.context = context;
    }

    public ChatRoomPsychologyHistoryAdapter() {

    }
    public void setData(List<ChatRoomPsychologyHistory> items){
        chatRoomPsychologyHistoryList = items;
        notifyDataSetChanged();
    }

    @Override
    public RoomChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_chat_history_psychology, parent, false);
        return new RoomChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomChatViewHolder holder, final int position) {

        for(int i=0; i<chatRoomPsychologyHistoryList.get(position).getParticipants().size(); i++){
            if(chatRoomPsychologyHistoryList.get(position).getParticipants().get(i).getEmail().equals(SaveUserData.getInstance().getUser().getId())){

                holder.tvRoomChatName.setText(chatRoomPsychologyHistoryList.get(position).getParticipants().get(i).getUsername());
            }
        }
        holder.tvLastMessage.setText("CLOSED");
        Picasso.with(context)
                .load(chatRoomPsychologyHistoryList.get(position).getRoomAvatarUrl())
                .into(holder.imgAvatarRoomChat);
        holder.itemView.setOnClickListener(view ->    onDetailListener.onItemDetailClicked(chatRoomPsychologyHistoryList.get(position).getRoomId()));

    }

    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }

    @Override
    public boolean onFailedToRecycleView(RoomChatViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if(chatRoomPsychologyHistoryList == null) return 0;
        return chatRoomPsychologyHistoryList.size();
    }

    public class RoomChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoomChatName, tvLastMessage, tvTimeStamp;
        ImageView imgAvatarRoomChat;


        public RoomChatViewHolder(final View itemView) {
            super(itemView);
            tvRoomChatName = itemView.findViewById(R.id.tv_room_chat_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            imgAvatarRoomChat = itemView.findViewById(R.id.img_avatar_room_chat);

        }
    }

    public interface OnDetailListener{
        void onItemDetailClicked(int idRoom);
    }
}
