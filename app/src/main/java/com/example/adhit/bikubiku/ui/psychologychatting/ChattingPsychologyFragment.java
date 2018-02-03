package com.example.adhit.bikubiku.ui.psychologychatting;


import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.ChatPschologyAdapter;
import com.example.adhit.bikubiku.data.local.SavePsychologyConsultationRoomChat;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.presenter.TransactionPresenter;
import com.example.adhit.bikubiku.receiver.CheckRoomIsBuildChatRoomReceiver;
import com.example.adhit.bikubiku.receiver.CheckRoomIsBuildReceiver;
import com.example.adhit.bikubiku.service.ChattingService;
import com.example.adhit.bikubiku.service.CheckRoomIsBuildChatRoomService;
import com.example.adhit.bikubiku.service.CheckRoomIsBuildService;
import com.example.adhit.bikubiku.ui.detailpsychologist.TransactionView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusAudioRecorderView;
import com.qiscus.sdk.ui.view.QiscusMentionSuggestionView;
import com.qiscus.sdk.ui.view.QiscusRecyclerView;
import com.qiscus.sdk.ui.view.QiscusReplyPreviewView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;


public class ChattingPsychologyFragment extends QiscusBaseChatFragment<ChatPschologyAdapter> implements ChattingPsychologyView, View.OnClickListener, CheckRoomIsBuildChatRoomReceiver.PeriodicCheckCarsReceiverListener, TransactionView {

    private ImageView mAttachButton;
    private LinearLayout mAddPanel;
    private View mInputPanel;
    private ChattingPsychologyPresenter chattingPsychologyPresenter;
    private TransactionPresenter transactionPresenter;
    private static boolean isHistory1;
    private CheckRoomIsBuildChatRoomReceiver mBroadcast;
    private Intent mService;
    private TextView tvFinish;



    public static ChattingPsychologyFragment newInstance(QiscusChatRoom qiscusChatRoom, boolean isHistory) {
        ChattingPsychologyFragment fragment = new ChattingPsychologyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAT_ROOM_DATA, qiscusChatRoom);
        isHistory1 = isHistory;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_chatting_psychology;
    }

    @Override
    protected void onLoadView(View view) {
        super.onLoadView(view);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 128);
        mInputPanel = view.findViewById(R.id.input_panel1);
       // getActivity().findViewById(R.id.tv_finish).setOnClickListener(v->chattingPsychologyPresenter.finishChat(getActivity(),qiscusChatRoom));
        View clickConsumer = view.findViewById(R.id.click_consumer);
        mAttachButton = (ImageView) view.findViewById(R.id.button_attach);
        mAddPanel = (LinearLayout) view.findViewById(R.id.add_panel);
        tvFinish = getActivity().findViewById(R.id.tv_finish);
        tvFinish.setOnClickListener(this);
        mAttachButton.setOnClickListener(v -> {
            if (mAddPanel.getVisibility() == View.GONE) {
                mAddPanel.startAnimation(animation);
                mAddPanel.setVisibility(View.VISIBLE);
                clickConsumer.setVisibility(View.VISIBLE);
            } else {
                mAddPanel.setVisibility(View.GONE);
                clickConsumer.setVisibility(View.GONE);
            }
        });
        if(isHistory1){
            getActivity().findViewById(R.id.tv_finish).setVisibility(View.GONE);
            mInputPanel.setVisibility(View.GONE);
        }
        clickConsumer.setOnClickListener(v -> {
            if (mAddPanel.getVisibility() == View.VISIBLE) {
                mAddPanel.setVisibility(View.GONE);
                clickConsumer.setVisibility(View.GONE);
            }
        });
//
    }

    @Override
    protected void onCreateChatComponents(Bundle savedInstanceState) {
        super.onCreateChatComponents(savedInstanceState);
        chattingPsychologyPresenter = new ChattingPsychologyPresenter(this);
        transactionPresenter = new TransactionPresenter(this);
//        if( SavePsychologyConsultationRoomChat.getInstance().getPsychologyConsultationRoomChat() ==0&& !isHistory1){
//
//            chattingPsychologyPresenter.sendFirstMessage(qiscusChatRoom);
//            SavePsychologyConsultationRoomChat.getInstance().savePsychologyConsultationRoomChat(qiscusChatRoom.getId());
//
//
//        }
        //check();
        if(!isHistory1){
            registerReceiver();
        }

    }

    @Override
    protected void onApplyChatConfig() {
        super.onApplyChatConfig();
        if (addImageButton != null) {
            addImageButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (takeImageButton != null) {
            takeImageButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (addFileButton != null) {
            addFileButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (recordAudioButton != null) {
            recordAudioButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
    }

    @NonNull
    @Override
    protected ViewGroup getRootView(View view) {
        return (ViewGroup) view.findViewById(R.id.root_view);
    }

    @Nullable
    @Override
    protected ViewGroup getEmptyChatHolder(View view) {
        return (ViewGroup) view.findViewById(R.id.empty_chat);
    }

    @NonNull
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout(View view) {
        return (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
    }

    @NonNull
    @Override
    protected QiscusRecyclerView getMessageRecyclerView(View view) {
        return (QiscusRecyclerView) view.findViewById(R.id.list_message);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageInputPanel(View view) {
        return (ViewGroup) view.findViewById(R.id.input_panel);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageEditTextContainer(View view) {
        return null;
    }

    @NonNull
    @Override
    protected EditText getMessageEditText(View view) {
        EditText editText = view.findViewById(R.id.field_message);
        return editText;
    }

    @NonNull
    @Override
    protected ImageView getSendButton(View view) {
        return (ImageView) view.findViewById(R.id.button_send);
    }

    @NonNull
    @Override
    protected QiscusMentionSuggestionView getMentionSuggestionView(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getNewMessageButton(View view) {
        return view.findViewById(R.id.button_new_message);
    }

    @NonNull
    @Override
    protected View getLoadMoreProgressBar(View view) {
        return view.findViewById(R.id.progressBar);
    }

    @Nullable
    @Override
    protected ImageView getEmptyChatImageView(View view) {
        return (ImageView) view.findViewById(R.id.empty_chat_icon);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatTitleView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_title);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatDescView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_desc);
    }

    @Nullable
    @Override
    protected ViewGroup getAttachmentPanel(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getAddImageLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_image);
    }

    @Nullable
    @Override
    protected View getTakeImageLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getTakeImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_pick_picture);
    }

    @Nullable
    @Override
    protected View getAddFileLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddFileButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_file);
    }

    @Nullable
    @Override
    protected View getRecordAudioLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getRecordAudioButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_audio);
    }

    @Nullable
    @Override
    protected View getAddContactLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddContactButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getAddLocationLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddLocationButton(View view) {
        return null;
    }

    @Nullable
    @Override
    public ImageView getHideAttachmentButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getToggleEmojiButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected QiscusAudioRecorderView getRecordAudioPanel(View view) {
        return (QiscusAudioRecorderView) view.findViewById(R.id.record_panel);
    }

    @Nullable
    @Override
    protected QiscusReplyPreviewView getReplyPreviewView(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getGotoBottomButton(View view) {
        return null;
    }

    @Override
    protected ChatPschologyAdapter onCreateChatAdapter() {
        return new ChatPschologyAdapter(getActivity(), qiscusChatRoom.isGroup());
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if(typing){
            ChattingPsychologyActivity.mSubtitle.setVisibility(View.VISIBLE);
            ChattingPsychologyActivity.mSubtitle.setText("Typing....");
        }else{
            ChattingPsychologyActivity.mSubtitle.setVisibility(View.GONE);
            ChattingPsychologyActivity.mSubtitle.setText("");
        }
    }

    public static void onUserChanged(boolean online){
        if(online){
            ChattingPsychologyActivity.mSubtitle.setVisibility(View.VISIBLE);
            ChattingPsychologyActivity.mSubtitle.setText("Online");
        }else{
            ChattingPsychologyActivity.mSubtitle.setVisibility(View.GONE);
            ChattingPsychologyActivity.mSubtitle.setText("");
        }
    }


    protected void recordAudio() {
        super.recordAudio();
        mAddPanel.setVisibility(View.GONE);
    }


    @Override
    public void sendFirstMessage(QiscusComment comment) {
        sendQiscusComment(comment);
    }

    @Override
    public void canCreateRoom(boolean b) {

    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {

    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {
        getActivity().stopService(new Intent(getContext(), ChattingService.class));
        getActivity().findViewById(R.id.tv_finish).setVisibility(View.GONE);
        mInputPanel.setVisibility(View.GONE);
        if(!isHistory1){
            unregisterReceiver();
            getActivity().stopService(mService);
        }
        sendQiscusComment(comment);

    }

    @Override
    public void onFailure(String failed) {

    }

    @Override
    public void onSuccessMakeTransaction(String berhasil) {

    }

    @Override
    public void onSuccessChangeTransactionStatus(String berhasil) {

        SaveUserData.getInstance().removeTransaction();
    }

    @Override
    public void onFinishTransaction() {
             SessionChatPsychology.getInstance().setRoomChatPsychologyConsultationIsBuild(false);
             SavePsychologyConsultationRoomChat.getInstance().removePsychologyConsultationRoomChat();
//        SaveUserData.getInstance().removeEndTimeOfTransaction();
//        SaveUserData.getInstance().removeTransaction();
//        SaveUserData.getInstance().removeEndTimeOfTransaction();
//        SavePsychologyConsultationRoomChat.getInstance().removePsychologyConsultationRoomChat();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.tv_finish){
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(SavePsychologyConsultationRoomChat.getInstance().getPsychologyConsultationRoomChat());
            transactionPresenter.changeTransacationStatus("Psikologi", SaveUserData.getInstance().getTransaction().getInvoice(), qiscusChatRoom.getId(), "finish");
            chattingPsychologyPresenter.finishChat(getActivity(), qiscusChatRoom);

        }
    }

    @Override
    public void showComments(List<QiscusComment> qiscusComments) {
        super.showComments(qiscusComments);
        for(int i=0; i<qiscusComments.size();i++){
            JSONObject payload = null;

            try {
                if(!qiscusComments.get(i).getMessage().isEmpty()){
                    if(!qiscusComments.get(i).getExtraPayload().equals("null")){
                        payload = new JSONObject(qiscusComments.get(i).getExtraPayload());
                    }
                    if( payload.get("type").equals("closed_chat")){
                        mInputPanel.setVisibility(View.GONE);
                        getActivity().findViewById(R.id.tv_finish).setVisibility(View.GONE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNewComment(QiscusComment qiscusComment) {
        super.onNewComment(qiscusComment);
        JSONObject payload = null;
        try {
            payload = new JSONObject(qiscusComment.getExtraPayload());

            if (payload.optString("type").equals("closed_chat")) {
                mInputPanel.setVisibility(View.GONE);
                getActivity().findViewById(R.id.tv_finish).setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void registerReceiver() {
        mBroadcast = new CheckRoomIsBuildChatRoomReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(CheckRoomIsBuildChatRoomReceiver.TAG);
        getActivity().registerReceiver(mBroadcast, filter);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(!isHistory1){
            mService = new Intent(getActivity(), CheckRoomIsBuildChatRoomService.class);
            getActivity().startService(mService);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!isHistory1){
            getActivity().stopService(mService);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!isHistory1){
            unregisterReceiver();
        }
    }

    private void unregisterReceiver() {
        try {
            if (mBroadcast != null) {
                getActivity().unregisterReceiver(mBroadcast);
            }
        } catch (Exception e) {
            Log.i("", "broadcastReceiver is already unregistered");
            mBroadcast = null;
        }

    }


    @Override
    public void handleFromReceiver(boolean isRoomBuild) {
        if(!isRoomBuild){
            mInputPanel.setVisibility(View.GONE);
            getActivity().findViewById(R.id.tv_finish).setVisibility(View.GONE);
        }else{
            mInputPanel.setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.tv_finish).setVisibility(View.VISIBLE);
        }
    }
}
