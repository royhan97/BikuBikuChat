package com.example.adhit.bikubiku.ui.ruangBelajarChatting;



import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adhit.bikubiku.BikuBiku;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.CustomChatAdapter;
import com.example.adhit.bikubiku.data.local.SavePsychologyConsultationRoomChat;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.local.SaveUserTrxPR;
import com.example.adhit.bikubiku.data.local.Session;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.presenter.RuangBelajarPresenter;
import com.example.adhit.bikubiku.receiver.EndChatStatusReceiver;
import com.example.adhit.bikubiku.service.IsRuangBelajarEndService;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.google.gson.JsonObject;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.NotificationClickListener;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.ui.adapter.QiscusChatAdapter;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusAudioRecorderView;
import com.qiscus.sdk.ui.view.QiscusMentionSuggestionView;
import com.qiscus.sdk.ui.view.QiscusRecyclerView;
import com.qiscus.sdk.ui.view.QiscusReplyPreviewView;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created on : September 28, 2016
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class RuangBelajarFragment extends QiscusBaseChatFragment<QiscusChatAdapter> implements RuangBelajarView, View.OnClickListener,EndChatStatusReceiver.
        PeriodicCheckCarsReceiverListener {
    private ImageView mAttachButton;
    private LinearLayout mAddPanel;
    private LinearLayout mInputPanel;
    private RuangBelajarPresenter ruangBelajarPresenter;
    private TextView tv_endChat, mSubtitle;
    private static boolean isHistory;
    private EndChatStatusReceiver mBroadcast;
    private Intent mService;
    private boolean isOnline;
    private Date lastActive;

    public static RuangBelajarFragment newInstance(QiscusChatRoom qiscusChatRoom, boolean isHistory) {
        RuangBelajarFragment.isHistory = isHistory;
        RuangBelajarFragment fragment = new RuangBelajarFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAT_ROOM_DATA, qiscusChatRoom);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_ruang_belajar;
    }

    @Override
    protected void onLoadView(View view) {
        super.onLoadView(view);
        swipeRefreshLayout.setProgressViewOffset(false,0,100);
        View clickConsumer = view.findViewById(R.id.click_consumer);
        mAttachButton = (ImageView) view.findViewById(R.id.button_attach);
        mAddPanel = (LinearLayout) view.findViewById(R.id.add_panel);
        mInputPanel = (LinearLayout) view.findViewById(R.id.input_panel1);
        tv_endChat = getActivity().findViewById(R.id.tv_end_chat);
//        mSubtitle = (TextView)getActivity().findViewById(R.id.tv_subtitle);
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

        clickConsumer.setOnClickListener(v -> {
            if (mAddPanel.getVisibility() == View.VISIBLE) {
                mAddPanel.setVisibility(View.GONE);
                clickConsumer.setVisibility(View.GONE);
            }
        });

        if (SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
            tv_endChat.setVisibility(View.GONE);
        }

        registerReceiver();
    }

    private void sendLockedMessage() {
        String username = "";
        for (int i=0;i<qiscusChatRoom.getMember().size();i++){
            if (!qiscusChatRoom.getMember().get(i).getEmail().equals(SaveUserData.getInstance().getUser().getId())){
                username = qiscusChatRoom.getMember().get(i).getUsername();
            }
        }
        JSONObject payload = new JSONObject();
        try {
            payload.put("locked", "halo")
                    .put("description1", "Silahkan mulai chatting dengan "+ username +", waktu anda adalah 1 jam.")
                    .put("description2", "Silahkan mulai chatting dengan "+ SaveUserData.getInstance().getUser().getUsername() +", waktu anda adalah 1 jam.")
                    .put("layanan", SaveUserTrxPR.getInstance().getTrx().getLayanan())
                    .put("invoice", SaveUserTrxPR.getInstance().getTrx().getInvoice())
                    .put("idBiquers", SaveUserTrxPR.getInstance().getTrx().getIdBiquers());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        QiscusComment comment = QiscusComment.generateCustomMessage(SaveUserData.getInstance().getUser().getUsername()+" ingin konsultasi pr dengan anda", "lock_message", payload,
                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());

        sendQiscusComment(comment);
    }

    @Override
    protected void onCreateChatComponents(Bundle savedInstanceState) {
        super.onCreateChatComponents(savedInstanceState);
        ruangBelajarPresenter = new RuangBelajarPresenter(this);
        SharedPrefUtil.saveInt(Constant.ROOM_ID, qiscusChatRoom.getId());
        if (!Session.getInstance().isBuildRoomRuangBelajar() && !RuangBelajarFragment.isHistory && !SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
            Session.getInstance().setBuildRoomRuangBelajar(true);
            System.out.println("status room build " + SharedPrefUtil.getBoolean(Constant.IS_ROOM_BUILD_RUANG_BELAJAR));
            sendLockedMessage();
            SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, false);
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

    @Override
    public void onResume() {
        super.onResume();
        if (RuangBelajarFragment.isHistory){
            tv_endChat.setAlpha(0.3f);
            tv_endChat.setOnClickListener(null);
            mInputPanel.setVisibility(View.GONE);
        }
        else {
            if (SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
                SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING,false);
            }
            mInputPanel.setVisibility(View.VISIBLE);
            tv_endChat.setOnClickListener(this);
        }

    }

    public void registerReceiver() {
        mBroadcast = new EndChatStatusReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(EndChatStatusReceiver.TAG);
        getActivity().registerReceiver(mBroadcast, filter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mService = new Intent(getActivity(), IsRuangBelajarEndService.class);
        getActivity().startService(mService);

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(mService);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(mBroadcast);
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
        return (EditText) view.findViewById(R.id.field_message);
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

//    @Nullable
//    @Override
//    protected ImageView getToggleEmojiButton(View view) {
//        return (ImageView) view.findViewById(R.id.button_emoji);
//    }

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
    protected QiscusChatAdapter onCreateChatAdapter() {
        //return new QiscusChatAdapter(getActivity(), qiscusChatRoom.isGroup());
        return new CustomChatAdapter(getActivity(), qiscusChatRoom.isGroup());
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        System.out.println("typing : "+typing + " is online : " + isOnline);
        if(typing){
            RuangBelajarChatting.mSubtitle.setText(R.string.text_typing);
        }
    }


    protected void recordAudio() {
        super.recordAudio();
        mAddPanel.setVisibility(View.GONE);
    }

    @Override
    public void setupCustomChatConfig() {
        Qiscus.getChatConfig()
                .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off);
    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {
        sendQiscusComment(comment);
//        getActivity().finish();
    }

    @Override
    public void openChatRoom(QiscusChatRoom qiscusChatRoom) {

    }

    @Override
    public void showMessageClosedChatFromService(String status) {

    }

    @Override
    public void onUserOnline(String user, boolean isOnline, Date lastActive) {
        this.isOnline = isOnline;
        this.lastActive = lastActive;
        if(isOnline){
            RuangBelajarChatting.mSubtitle.setText(R.string.text_online);
        }
        else {
            RuangBelajarChatting.mSubtitle.setText(R.string.text_offline);
        }
    }

    @Override
    public void openWaitingActivity() {

    }

    @Override
    public void setIdRoom(int idRoom) {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_end_chat){
//            getActivity().onBackPressed();
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(SharedPrefUtil.getInt(Constant.ROOM_ID));
            ruangBelajarPresenter.finishChat(qiscusChatRoom);
            Session.getInstance().setBuildRoomRuangBelajar(false);
            SharedPrefUtil.saveBoolean(Constant.IS_END_CHATTING, true);
            mInputPanel.setVisibility(View.GONE);
            tv_endChat.setAlpha(0.3f);
            tv_endChat.setOnClickListener(null);
        }
    }

    @Override
    public void handleFromEndChat(boolean isEndChat) {
        if (isEndChat){
            if (SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
                mInputPanel.setVisibility(View.GONE);
                Session.getInstance().setBuildRoomRuangBelajar(false);
            }
            else {
                tv_endChat.setAlpha(0.3f);
                tv_endChat.setOnClickListener(null);
                mInputPanel.setVisibility(View.GONE);
                Session.getInstance().setBuildRoomRuangBelajar(false);
                ruangBelajarPresenter.updateStatusTransaksiPR(getActivity(), SaveUserTrxPR.getInstance().getTrx().getLayanan(),
                        SaveUserTrxPR.getInstance().getTrx().getInvoice(),SharedPrefUtil.getInt(Constant.ROOM_ID), "finish");
            }
        }
    }

    @Override
    public void handleFromIsRoomEnd(boolean isRoomEnd) {
        if (isRoomEnd){
            if (SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
                mInputPanel.setVisibility(View.GONE);
            }
            else {
                tv_endChat.setAlpha(0.3f);
                tv_endChat.setOnClickListener(null);
                mInputPanel.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void handleFromEndWait(boolean isEndWait) {

    }

}
