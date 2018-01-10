package com.example.adhit.bikubiku.ui.detailpsychologist;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Psychologist;
import com.example.adhit.bikubiku.presenter.ChangePasswordPresenter;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.service.ChattingPsychologyService;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPsychologistFragment extends Fragment implements View.OnClickListener, ChattingPsychologyView {


    private TextView tvPsychologistName, tvPsychologistPriceConsultation;
    private Button btnNext;
    private ImageView imgPsychologist;
    private Bundle bundle;
    private Psychologist psychologist;
    public DetailPsychologistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Detail Psikolog");
        setHasOptionsMenu(true);

        View view =  inflater.inflate(R.layout.fragment_detail_psychologist, container, false);
        tvPsychologistName = view.findViewById(R.id.tv_psychologist_name);
        tvPsychologistPriceConsultation = view.findViewById(R.id.tv_price_consultation);
        imgPsychologist = view.findViewById(R.id.img_psychologist);
        btnNext = view.findViewById(R.id.btn_next);
        initView();
        return view;
    }

    public void initView(){
        bundle = getArguments();
        psychologist = bundle.getParcelable(Constant.PSYCHOLOGIST);
        tvPsychologistName.setText(psychologist.getNama());
        tvPsychologistPriceConsultation.setText(psychologist.getPrice());
        btnNext.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction().remove(this).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_next){
            ChattingPsychologyPresenter chattingPsychologyPresenter = new ChattingPsychologyPresenter(this);
            chattingPsychologyPresenter.createRoomChat(getActivity(), psychologist.getId(), psychologist.getNama());
        }
    }

    @Override
    public void sendFirstMessage(QiscusComment comment) {

    }

    @Override
    public void canCreateRoom(boolean b) {
        if(b){
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction().remove(this).commit();
        }else{
            ShowAlert.showToast(getActivity(), "Gagal");
        }

    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent mStartIntentService = new Intent(getActivity(), ChattingPsychologyService.class);
        mStartIntentService.putExtra(ChattingPsychologyService.EXTRA_DURATION, 20000);
        mStartIntentService.putExtra(ChattingPsychologyService.QISCUS_CHAT_ROOM, qiscusChatRoom);
        getActivity().startService(mStartIntentService);

        Intent intent= ChattingPsychologyActivity.generateIntent(getActivity(), qiscusChatRoom, false);
        startActivity(intent);

    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void showMessageClosedChatFromService(String success) {

    }
}
