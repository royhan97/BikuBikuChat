package com.example.adhit.bikubiku.ui.listpsychologist;


import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.PsychologistAdapter;
import com.example.adhit.bikubiku.data.local.SavePsychologyConsultationRoomChat;
import com.example.adhit.bikubiku.data.model.Psychologist;
import com.example.adhit.bikubiku.data.model.PsychologistApprove;
import com.example.adhit.bikubiku.presenter.ListPsychologistPresenter;
import com.example.adhit.bikubiku.receiver.CheckRoomIsBuildReceiver;
import com.example.adhit.bikubiku.service.CheckRoomIsBuildService;
import com.example.adhit.bikubiku.ui.detailpsychologist.DetailPsychologistFragment;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.loadingtransaction.LoadingTransactionActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.util.Constant;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPsychologistFragment extends Fragment implements View.OnClickListener, ListPsychologistView, PsychologistAdapter.OnDetailListener, CheckRoomIsBuildReceiver.PeriodicCheckCarsReceiverListener {

    private RecyclerView rvPhycologist;
    private ListPsychologistPresenter psychologyConsultationPresenter;
    private PsychologistAdapter psychologistAdapter;
    private RelativeLayout rlBlock;
    private TextView tvGoToChat;
    private CheckRoomIsBuildReceiver mBroadcast;
    private Intent mService;
    private ProgressBar pbLoading;
    private TextView tvError;
    private SwipeRefreshLayout srlPsychologist;

    public ListPsychologistFragment() {
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
        pbLoading = view.findViewById(R.id.pb_loading);
        tvError = view.findViewById(R.id.tv_error);
        srlPsychologist = view.findViewById(R.id.srl_psychologist);
        srlPsychologist.setOnRefreshListener(() -> {
            tvError.setText("");
            pbLoading.setVisibility(View.VISIBLE);
            rvPhycologist.setVisibility(View.GONE);
            psychologyConsultationPresenter.psychologyList();
            srlPsychologist.setRefreshing(false);  });

        registerReceiver();
        initView();
        return  view;
    }

    public void initView(){
        psychologistAdapter = new PsychologistAdapter(getActivity());
        psychologistAdapter.setOnClickDetailListener(this);
        rvPhycologist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPhycologist.setAdapter(psychologistAdapter);
        tvGoToChat.setOnClickListener(this);
        psychologyConsultationPresenter = new ListPsychologistPresenter(this);
        psychologyConsultationPresenter.psychologyList();
        psychologyConsultationPresenter.isRoomChatBuild();
    }
    public void registerReceiver() {
        mBroadcast = new CheckRoomIsBuildReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(CheckRoomIsBuildReceiver.TAG);
        getActivity().registerReceiver(mBroadcast, filter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mService = new Intent(getActivity(), CheckRoomIsBuildService.class);
        getActivity().startService(mService);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
        getActivity().stopService(mService);
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
    public void showData(List<PsychologistApprove> psychologistArrayList) {
        rvPhycologist.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
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
        Intent intent = ChattingPsychologyActivity.generateIntent(getActivity(), qiscusChatRoom, false);
        startActivity(intent);
    }

    @Override
    public void onFailure(String s) {
        tvError.setText(s);
        rvPhycologist.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.img_logo).setVisibility(View.VISIBLE);
            ((HomeActivity)getActivity()).getSupportActionBar().setTitle("");
            ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getFragmentManager().beginTransaction().
                    replace(R.id.frame_container,new HomeFragment())
                    .commit();
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
        psychologyConsultationPresenter.isRoomChatBuild();
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Konsultasi Psikologi");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onItemDetailClicked(PsychologistApprove psychologistApprove) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.PSYCHOLOGIST, psychologistApprove);
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
            if(SavePsychologyConsultationRoomChat.getInstance().getPsychologyConsultationRoomChat()==0){
                Intent intent = new Intent(getActivity(), LoadingTransactionActivity.class);
                startActivity(intent);
            }else{
                psychologyConsultationPresenter.openRoomChat(getActivity());
            }

        }
    }

    @Override
    public void handleFromReceiver(boolean isRoomBuild) {
        if(isRoomBuild){
            rlBlock.setVisibility(View.VISIBLE);
            rvPhycologist.setClickable(false);
        }else {
            rlBlock.setVisibility(View.GONE);
        }
        psychologyConsultationPresenter.psychologyList();
    }
}
