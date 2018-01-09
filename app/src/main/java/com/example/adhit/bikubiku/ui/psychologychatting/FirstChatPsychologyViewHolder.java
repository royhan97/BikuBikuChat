package com.example.adhit.bikubiku.ui.psychologychatting;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.OnItemClickListener;
import com.qiscus.sdk.ui.adapter.OnLongItemClickListener;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;

import org.json.JSONObject;

/**
 * Created by adhit on 08/01/2018.
 */

public class FirstChatPsychologyViewHolder extends QiscusBaseMessageViewHolder<QiscusComment> {
    private TextView tvUser, tvUserResult;

    public FirstChatPsychologyViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        //tvUser = itemView.findViewById(R.id.tv_name_user_test);
      //  tvUserResult = itemView.findViewById(R.id.tv_test_result);
    }

    @Nullable
    @Override
    protected ImageView getFirstMessageBubbleIndicatorView(View itemView) {
        return null;
    }

    @NonNull
    @Override
    protected View getMessageBubbleView(View itemView) {
        return itemView.findViewById(R.id.cv_first_chat_psychology);
    }

    @Nullable
    @Override
    protected TextView getDateView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected TextView getTimeView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getMessageStateIndicatorView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAvatarView(View itemView) {
        return null;
    }

    @Nullable
    @Override
    protected TextView getSenderNameView(View itemView) {
        return null;
    }

    @Override
    protected void showMessage(QiscusComment qiscusComment) {
        try {
            JSONObject payload = new JSONObject(qiscusComment.getExtraPayload());
           // tvUserResult.setText(payload.optJSONObject("content").optString("description"));
        } catch (Exception e) {
            //tvUserResult.setText(qiscusComment.getMessage());
        }
    }
}
