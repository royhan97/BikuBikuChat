package com.example.adhit.bikubiku.ui.listpsychologistchattinghistory;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.ChatRoomPsychologyHistoryAdapter;
import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;
import com.example.adhit.bikubiku.presenter.ListChattingPsychologistHistoryPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListChattingPsychologistHistoryFragment extends Fragment implements ListChattingPsychologistHistoryView, ChatRoomPsychologyHistoryAdapter.OnDetailListener {

    private RecyclerView rvChatPsychologyHistory;
    private ListChattingPsychologistHistoryPresenter chattingHistoryPsychologyPresenter;
    private ChatRoomPsychologyHistoryAdapter chatRoomPsychologyHistoryAdapter;
    private ProgressBar pbLoading;
    private TextView tvError;
    private SwipeRefreshLayout srlChatRoomHistory;

    public ListChattingPsychologistHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("History Konsultasi");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list_chatting_psychology_history, container, false);
        rvChatPsychologyHistory = view.findViewById(R.id.rv_chat_room_psychology_history);
        pbLoading = view.findViewById(R.id.pb_loading);
        tvError = view.findViewById(R.id.tv_error);
        srlChatRoomHistory = view.findViewById(R.id.srl_chat_room_psychology_history);
        srlChatRoomHistory.setOnRefreshListener(() -> {
            tvError.setText("");
            pbLoading.setVisibility(View.VISIBLE);
            rvChatPsychologyHistory.setVisibility(View.GONE);
            chattingHistoryPsychologyPresenter.getChattingHistoryList();
            srlChatRoomHistory.setRefreshing(false);  });
        initView();
        return view;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
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
    public void initView(){
        chattingHistoryPsychologyPresenter = new ListChattingPsychologistHistoryPresenter(this);
        chattingHistoryPsychologyPresenter.getChattingHistoryList();
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
    public void showData(List<ChatRoomPsychologyHistory> chatRoomPsychologyHistoryList) {
        pbLoading.setVisibility(View.GONE);
        rvChatPsychologyHistory.setVisibility(View.VISIBLE);
        chatRoomPsychologyHistoryAdapter.setData(chatRoomPsychologyHistoryList);
    }

    @Override
    public void onFailure(String s) {
        pbLoading.setVisibility(View.GONE);
        tvError.setText(s);
        ShowAlert.showToast(getActivity(), s);
    }

    @Override
    public void onItemDetailClicked(int idRoom) {
        chattingHistoryPsychologyPresenter.openRoomChatPsychologyHistoryById(getActivity(), idRoom);
    }

    @Override
    public void showMessage() {
        ShowAlert.showToast(getActivity(), getResources().getString(R.string.text_no_history_chat));
    }

    @Override
    public void openRoomChat(QiscusChatRoom qiscusChatRoom) {
        Intent intent = ChattingPsychologyActivity.generateIntent(getActivity(), qiscusChatRoom, true);
        startActivity(intent);
    }

}
