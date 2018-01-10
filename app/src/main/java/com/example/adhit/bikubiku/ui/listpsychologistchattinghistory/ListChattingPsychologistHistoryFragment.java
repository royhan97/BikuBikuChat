package com.example.adhit.bikubiku.ui.listpsychologistchattinghistory;


import android.app.Fragment;
import android.content.Intent;
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
import com.example.adhit.bikubiku.presenter.ListChattingPsychologistHistoryPresenter;
import com.example.adhit.bikubiku.presenter.ChattingPsychologyPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListChattingPsychologistHistoryFragment extends Fragment implements ListChattingPsychologistHistoryView, ChatRoomPsychologyHistoryAdapter.OnDetailListener, ChattingPsychologyView {

    private RecyclerView rvChatPsychologyHistory;
    private ListChattingPsychologistHistoryPresenter chattingHistoryPsychologyPresenter;
    private ChatRoomPsychologyHistoryAdapter chatRoomPsychologyHistoryAdapter;
    private ChattingPsychologyPresenter chattingPsychologyPresenter;

    public ListChattingPsychologistHistoryFragment() {
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
        chattingHistoryPsychologyPresenter = new ListChattingPsychologistHistoryPresenter(this);
        chattingHistoryPsychologyPresenter.getChattingHistoryList();
        chattingPsychologyPresenter = new ChattingPsychologyPresenter(this);
        chatRoomPsychologyHistoryAdapter = new ChatRoomPsychologyHistoryAdapter(getActivity());
        chatRoomPsychologyHistoryAdapter.setOnClickDetailListener(this);
        rvChatPsychologyHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatPsychologyHistory.setAdapter(chatRoomPsychologyHistoryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        chattingHistoryPsychologyPresenter.getChattingHistoryList();
    }

    @Override
    public void showData(List<ChatRoomPsychologyHistory> carList) {
        chatRoomPsychologyHistoryAdapter.setData(carList);
    }

    @Override
    public void onFailure(String s) {
        ShowAlert.showToast(getActivity(), s);
    }

    @Override
    public void onItemDetailClicked(int idRoom) {
        chattingPsychologyPresenter.openRoomChatPsychologyHistoryById(idRoom);
    }

    @Override
    public void sendFirstMessage(QiscusComment comment) {

    }

    @Override
    public void canCreateRoom(boolean b) {

    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChattingPsychologyActivity.generateIntent(getActivity(), qiscusChatRoom, true);
        startActivity(intent);
    }

    @Override
    public void sendClosedMessage(QiscusComment comment) {

    }

    @Override
    public void showMessageClosedChatFromService(String success) {

    }
}
