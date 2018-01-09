package com.example.adhit.bikubiku.ui.psychologychattinghistory;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.ChatRoomPsychologyHistoryAdapter;
import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;
import com.example.adhit.bikubiku.presenter.ChattingHistoryPsychologyPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.util.ShowAlert;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingPsychologyHistoryFragment extends Fragment implements ChattingPsychologyHistoryView {

    private RecyclerView rvChatPsychologyHistory;
    private ChattingHistoryPsychologyPresenter chattingHistoryPsychologyPresenter;
    private ChatRoomPsychologyHistoryAdapter chatRoomPsychologyHistoryAdapter;

    public ChattingPsychologyHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("History Konsultasi");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_chatting_psychology_history, container, false);
        rvChatPsychologyHistory = view.findViewById(R.id.rv_chat_room_psychology_history);
        initView();
        return view;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public void initView(){
        chattingHistoryPsychologyPresenter = new ChattingHistoryPsychologyPresenter(this);
        chattingHistoryPsychologyPresenter.getChattingHistoryList();
        chatRoomPsychologyHistoryAdapter = new ChatRoomPsychologyHistoryAdapter(getActivity());
        rvChatPsychologyHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatPsychologyHistory.setAdapter(chatRoomPsychologyHistoryAdapter);
    }

    @Override
    public void showData(List<ChatRoomPsychologyHistory> carList) {
        chatRoomPsychologyHistoryAdapter.setData(carList);
    }

    @Override
    public void onFailure(String s) {
        ShowAlert.showToast(getActivity(), s);
    }
}
