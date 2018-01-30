package com.example.adhit.bikubiku.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserTrxPR;
import com.example.adhit.bikubiku.data.model.DetailKabim;
import com.example.adhit.bikubiku.data.model.Kabim;
import com.example.adhit.bikubiku.presenter.RuangBelajarPresenter;
import com.example.adhit.bikubiku.service.RuangBelajarEndChattingService;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChatting;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.ui.waitingrequestresponse.WaitingRequestResponActivity;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.Date;
import java.util.List;

/**
 * Created by roy on 12/10/2017.
 */

public class KabimAdapter extends ExpandableRecyclerViewAdapter<KabimVIewHolder, DetailViewHolder> implements RuangBelajarView {

    private Context context;
    private RuangBelajarPresenter presenter;

    public KabimAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public KabimVIewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kabim, parent, false);
        return new KabimVIewHolder(context, view);
    }

    @Override
    public DetailViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kabim_detail, parent, false);
        return new DetailViewHolder(context, view);
    }

    @Override
    public void onBindChildViewHolder(DetailViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final DetailKabim detail = ((Kabim) group).getItems().get(childIndex);
        String deskripsi = detail.getDescription();
        if (deskripsi == "nu" +
                "ll"){
            deskripsi = "No Description";
        }
        else {
            deskripsi = detail.getDescription();
        }
        System.out.println("deskripsi produk : " + deskripsi);
        holder.setContentDeatail(deskripsi, detail.getReviews());
    }

    @Override
    public void onBindGroupViewHolder(KabimVIewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setProduct(group);
        if (!SharedPrefUtil.getBoolean(Constant.IS_ROOM_BUILD_RUANG_BELAJAR)){
            holder.chat.setAlpha(1.0F);
            holder.chat.setOnClickListener(view -> {
                presenter = new RuangBelajarPresenter(this);
                Toast.makeText(context, ((Kabim) group).getNameKabim(), Toast.LENGTH_SHORT).show();
                presenter.createTrx(context,"konsultasipr",2,15,1,"Tidak ada");
//                presenter.createRoomChat(context, ((Kabim)group).getIdKabim(), ((Kabim) group).getNameKabim());
            });
        }
        else {
            holder.chat.setAlpha(0.25F);
        }

    }

    @Override
    public void setupCustomChatConfig() {
        Qiscus.getChatConfig()
                .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off);
    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void openChatRoom(QiscusChatRoom qiscusChatRoom) {
        if (!SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
            Intent intentService = new Intent(context, RuangBelajarEndChattingService.class);
            intentService.putExtra(RuangBelajarEndChattingService.EXTRA_DURATION, 60000);
            intentService.putExtra(RuangBelajarEndChattingService.QISCUS_CHAT_ROOM, qiscusChatRoom);
            context.startService(intentService);
        }

        Intent intent = RuangBelajarChatting.generateIntent(context, qiscusChatRoom, false);
        context.startActivity(intent);
        ShowAlert.closeProgresDialog();
    }

    @Override
    public void showMessageClosedChatFromService(String status) {

    }

    @Override
    public void onUserOnline(String user, boolean isOnline, Date lastActive) {

    }

    @Override
    public void openWaitingActivity() {
        Intent intent = new Intent(context, WaitingRequestResponActivity.class);
        intent.putExtra("layanan", SaveUserTrxPR.getInstance().getTrx().getLayanan());
        intent.putExtra("invoice", SaveUserTrxPR.getInstance().getTrx().getInvoice());
        context.startActivity(intent);
    }

    @Override
    public void setIdRoom(int idRoom) {

    }


}
