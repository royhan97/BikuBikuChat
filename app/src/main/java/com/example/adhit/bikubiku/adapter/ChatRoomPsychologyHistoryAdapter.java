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
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
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

        holder.tvRoomChatName.setText(chatRoomPsychologyHistoryList.get(position).getNamaKabim());
        holder.tvLastMessage.setText("CLOSED");
        holder.tvChatDate.setText(chatRoomPsychologyHistoryList.get(position).getCreateDate() );
        Picasso.with(context)
                .load(R.drawable.avatar1)
                .into(holder.imgAvatarRoomChat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chatRoomPsychologyHistoryList.get(position).getIdRoom()== null){
                    onDetailListener.showMessage();
                }else {
                    onDetailListener.onItemDetailClicked(Integer.parseInt((String) chatRoomPsychologyHistoryList.get(position).getIdRoom()));
                }
            }
        });
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
        TextView tvRoomChatName, tvLastMessage, tvChatDate;
        ImageView imgAvatarRoomChat;


        public RoomChatViewHolder(final View itemView) {
            super(itemView);
            tvRoomChatName = itemView.findViewById(R.id.tv_room_chat_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvChatDate = itemView.findViewById(R.id.tv_chat_date);
            imgAvatarRoomChat = itemView.findViewById(R.id.img_avatar_room_chat);

        }
    }

    public  String unixTimestamptoDate(int unixTimestamp){
        Date date = new Date(unixTimestamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public  String dateToString(Date date1){
        Date date = new Date(String.valueOf(date1));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public interface OnDetailListener{
        void onItemDetailClicked(int idRoom);

        void showMessage();
    }
}