package com.example.adhit.bikubiku.ui.psychologyconsultation;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.PsychologistAdapter;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.model.Psychologist;
import com.example.adhit.bikubiku.presenter.PsychologyConsultationPresenter;
import com.example.adhit.bikubiku.ui.detailpsychologist.DetailPsychologistFragment;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PsychologyConsultationFragment extends Fragment implements View.OnClickListener, PsychologyConsultationView, PsychologistAdapter.OnDetailListener {

    private RecyclerView rvPhycologist;
    private PsychologyConsultationPresenter psychologyConsultationPresenter;
    private PsychologistAdapter psychologistAdapter;
    private RelativeLayout rlBlock;
    private TextView tvGoToChat;

    public PsychologyConsultationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Konsultasi Psikologi");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_phychology_consultation, container, false);
        rvPhycologist = view.findViewById(R.id.rv_pschologist);
        rlBlock = view.findViewById(R.id.rl_block);
        tvGoToChat = view.findViewById(R.id.tv_go_to_chat);

        initView();
        return  view;
    }

    public void initView(){
        psychologistAdapter = new PsychologistAdapter(getActivity());
        psychologistAdapter.setOnClickDetailListener(this);
        rvPhycologist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPhycologist.setAdapter(psychologistAdapter);
        rlBlock.setVisibility(View.GONE);
        tvGoToChat.setOnClickListener(this);
        psychologyConsultationPresenter = new PsychologyConsultationPresenter(this);
        psychologyConsultationPresenter.psychologyList();
        psychologyConsultationPresenter.isRoomChatBuild();
    }


    @Override
    public void showData(ArrayList<Psychologist> psychologistArrayList) {
        psychologistAdapter.setData(psychologistArrayList);
    }

    @Override
    public void showBlock(boolean roomChatPsychologyConsultationBuild) {
        if(roomChatPsychologyConsultationBuild){
            rlBlock.setVisibility(View.VISIBLE);
            rvPhycologist.setClickable(false);
            psychologyConsultationPresenter.psychologyList();
        }else {
            rlBlock.setVisibility(View.GONE);
            psychologyConsultationPresenter.psychologyList();
        }
    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChattingPsychologyActivity.generateIntent(getActivity(), qiscusChatRoom);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        psychologyConsultationPresenter.isRoomChatBuild();
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Konsultasi Psikologi");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onItemDetailClicked(Psychologist psychologist) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.PSYCHOLOGIST, psychologist);
        DetailPsychologistFragment detailPsychologist = new DetailPsychologistFragment();
        detailPsychologist.setArguments(bundle);
         getFragmentManager().beginTransaction().
               replace(R.id.frame_container,
                        detailPsychologist).
                 addToBackStack(DetailPsychologistFragment.class.getSimpleName()).commit();

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_go_to_chat){
            psychologyConsultationPresenter.openRoomChat(getActivity());
        }
    }
}
