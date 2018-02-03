package com.example.adhit.bikubiku.ui.ruangBelajarChattingHistory;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.ChatRoomPsychologyHistoryAdapter;
import com.example.adhit.bikubiku.adapter.ChatRoomRuangBelajarHistoryAdapter;
import com.example.adhit.bikubiku.data.model.ChatRoomHistory;
import com.example.adhit.bikubiku.data.model.RequestToKabim;
import com.example.adhit.bikubiku.presenter.RuangBelajarChattingHistoryPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingKabim.RuangBelajarChattingKabimFragment;
import com.example.adhit.bikubiku.util.ShowAlert;

import java.util.List;

/**
 * Created by roy on 1/10/2018.
 */

public class RuangBelajarChattingHistoryFragment extends Fragment implements RuangBelajarChattingHistoryView {

    private RecyclerView rvChatRuangBelajarHistory;
    private RuangBelajarChattingHistoryPresenter ruangBelajarChattingHistoryPresenter;
    private ChatRoomRuangBelajarHistoryAdapter ruangBelajarChattingHistoryAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        getActivity().findViewById(R.id.item_history).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Riwayat Belajar");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_ruang_belajar_chatting_history, container, false);
        rvChatRuangBelajarHistory = view.findViewById(R.id.rv_listHistory);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        ruangBelajarChattingHistoryPresenter = new RuangBelajarChattingHistoryPresenter(this);
        ruangBelajarChattingHistoryPresenter.getChattingHistoryList();
        ruangBelajarChattingHistoryAdapter = new ChatRoomRuangBelajarHistoryAdapter(getActivity(), this);
        rvChatRuangBelajarHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatRuangBelajarHistory.setAdapter(ruangBelajarChattingHistoryAdapter);
    }

    @Override
    public void showData(List<ChatRoomHistory> chatRoomHistories) {
        ruangBelajarChattingHistoryAdapter.setData(chatRoomHistories);
    }

    @Override
    public void onFailure(String s) {
        ShowAlert.showToast(getActivity(), s);
    }

    @Override
    public void onDetailChatRoomHistory(int idRoom) {
        ruangBelajarChattingHistoryPresenter.openChatRoomHistoryWithId(getActivity(), idRoom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction().remove(this).commit();

        }
        return super.onOptionsItemSelected(item);
    }
}
