package com.example.adhit.bikubiku.ui.listKabim;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.KabimAdapter;
import com.example.adhit.bikubiku.data.model.Kabim;
import com.example.adhit.bikubiku.presenter.ListKabimFragmentPresenter;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarChatting;
import com.example.adhit.bikubiku.ui.ruangBelajarChatting.RuangBelajarView;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingHistory.RuangBelajarChattingHistoryFragment;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by roy on 1/5/2018.
 */

public class ListKabimFragment extends Fragment implements View.OnClickListener, ListKabimFragmentView {

    private View loadingIndicator;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private KabimAdapter adapter;
    private LinearLayout llContinueChat;
    private ListKabimFragmentPresenter presenterKabim;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Daftar Kabim");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list_kabim, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_kabim);
        llContinueChat = (LinearLayout) view.findViewById(R.id.ll_continue_chat);
        presenterKabim = new ListKabimFragmentPresenter(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        llContinueChat.setOnClickListener(view1 -> {
            ShowAlert.showProgresDialog(getActivity());
            presenterKabim.continueLastChat();
        });

        return view;
    }

    private void showList() {
        presenterKabim.getListKabim();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showData(List<Kabim> listKabim) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new KabimAdapter(getActivity(), listKabim);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void toRecentRoomChat(int id) {
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(id),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    String type, description;
                    Boolean isHistory = false;
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        try {
                            String strPayload = qiscusChatRoom.getLastComment().getExtraPayload();
                            JSONObject jsonObjectPayload = new JSONObject(strPayload);
                            JSONObject jsonObjectContent = jsonObjectPayload.getJSONObject("content");
                            System.out.println("extra comment : " + jsonObjectPayload);
                            type = jsonObjectPayload.getString("type");
                            description = jsonObjectContent.getString("description");
                            if ((type.equals("end_chat") && description.equals("Sesi Chat Berakhir")) || SharedPrefUtil.getBoolean(Constant.IS_END_CHATTING)) {
                                isHistory = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = RuangBelajarChatting.generateIntent(getActivity(), qiscusChatRoom, isHistory);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        showList();
        if (SharedPrefUtil.getBoolean(Constant.IS_ROOM_BUILD_RUANG_BELAJAR)){
            llContinueChat.setVisibility(View.VISIBLE);
        }
        else {
            llContinueChat.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_history, menu);
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
        if (item.getItemId() == R.id.item_history){
            getFragmentManager().beginTransaction().
                    replace(R.id.frame_container, new RuangBelajarChattingHistoryFragment())
                    .addToBackStack(RuangBelajarChattingHistoryFragment.class.getSimpleName())
                    .commit();
        }

        return  super.onOptionsItemSelected(item);
    }
}
