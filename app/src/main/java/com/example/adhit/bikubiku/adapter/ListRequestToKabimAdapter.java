package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.RequestToKabim;
import com.example.adhit.bikubiku.presenter.ListRequestToKabimPresenter;
import com.example.adhit.bikubiku.ui.listRequestToKabim.ListRequestToKabimView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roy on 1/23/2018.
 */

public class ListRequestToKabimAdapter extends RecyclerView.Adapter<ListRequestToKabimAdapter.ListRequestToKabimViewHolder> implements ListRequestToKabimView {

    private List<RequestToKabim> requestToKabimList;
    private Context context;
    private IRejectOrCancel iRejectOrCancel;


    public ListRequestToKabimAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RequestToKabim> items){
        requestToKabimList = items;
        notifyDataSetChanged();
    }

    public void setButtonOnClick(IRejectOrCancel iRejectOrCancel){
        this.iRejectOrCancel = iRejectOrCancel;
    }

    @Override
    public ListRequestToKabimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_to_kabim, parent, false);
        return new ListRequestToKabimAdapter.ListRequestToKabimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListRequestToKabimViewHolder holder, int position) {
        holder.tv_name.setText(requestToKabimList.get(position).getNamaBiquers());
        if (requestToKabimList.get(position).getFotoBiquers() != null){
            Picasso.with(context)
                    .load(requestToKabimList.get(position).getFotoBiquers())
                    .into(holder.img_room);
        }
        else {
            Picasso.with(context)
                    .load("https://www.shareicon.net/data/2016/08/05/806962_user_512x512.png")
                    .into(holder.img_room);
        }
        holder.btn_reject.setOnClickListener(v -> {
            iRejectOrCancel.onCancelClicked(requestToKabimList.get(position).getLayanan(),
                    requestToKabimList.get(position).getInvoice(),
                    "cancel");
            holder.cv_request.setVisibility(View.GONE);
        });

        holder.btn_accept.setOnClickListener(v -> {
            iRejectOrCancel.onAcceptClicked(requestToKabimList.get(position).getLayanan(),
                    requestToKabimList.get(position).getInvoice(),
                    requestToKabimList.get(position).getIdBiquers(),
                    requestToKabimList.get(position).getNamaBiquers(),
                    "accept");
            holder.cv_request.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        if (requestToKabimList == null) return 0;
        return requestToKabimList.size();
    }

    @Override
    public void setListRequestToKabim(List<RequestToKabim> requestToKabimList) {

    }

    @Override
    public void showError(String s) {
        ShowAlert.showToast(context, s);
    }


    public class ListRequestToKabimViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        Button btn_accept,btn_reject;
        ImageView img_room;
        CardView cv_request;

        public ListRequestToKabimViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.nameRoomBiquers);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_reject = itemView.findViewById(R.id.btn_reject);
            img_room = itemView.findViewById(R.id.avatarBiquers);
            cv_request = itemView.findViewById(R.id.cv_request);
        }
    }

    public interface IRejectOrCancel{

        void onCancelClicked(String layanan, String invoice, String status);

        void onAcceptClicked(String layanan, String invoice, String idBiquers, String name, String status);
    }
}
