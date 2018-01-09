package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.ui.psychologychatting.CloseChatPsychologyViewHolder;
import com.example.adhit.bikubiku.ui.psychologychatting.FirstChatPsychologyViewHolder;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.QiscusChatAdapter;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adhit on 08/01/2018.
 */

public class ChatPschologyAdapter extends QiscusChatAdapter {
    private static final int FIRST_CHAT_PSYCHOLOGY = 2304;
    private static final int CLOSE_CHAT_PSYCHOLOGY = 2305;


    public ChatPschologyAdapter(Context context, boolean groupChat) {
        super(context, groupChat);
    }

    @Override
    protected int getItemViewTypeCustomMessage(QiscusComment qiscusComment, int position) {
        try {
            JSONObject payload = new JSONObject(qiscusComment.getExtraPayload());
            if (payload.optString("type").equals("user_test")) {
                return FIRST_CHAT_PSYCHOLOGY;
            }
            if(payload.getString("type").equals("closed_chat")){
                return CLOSE_CHAT_PSYCHOLOGY;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.getItemViewTypeCustomMessage(qiscusComment, position);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        switch (viewType) {
            case FIRST_CHAT_PSYCHOLOGY:
                return R.layout.item_first_chat_psychology;

            case CLOSE_CHAT_PSYCHOLOGY:
                return R.layout.item_close_psychology;
            default:
                return super.getItemResourceLayout(viewType);
        }
    }

    @Override
    public QiscusBaseMessageViewHolder<QiscusComment> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case FIRST_CHAT_PSYCHOLOGY:
                return new FirstChatPsychologyViewHolder(getView(parent, viewType), itemClickListener, longItemClickListener);
            case CLOSE_CHAT_PSYCHOLOGY:
                return new CloseChatPsychologyViewHolder(getView(parent, viewType), itemClickListener, longItemClickListener);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

}



