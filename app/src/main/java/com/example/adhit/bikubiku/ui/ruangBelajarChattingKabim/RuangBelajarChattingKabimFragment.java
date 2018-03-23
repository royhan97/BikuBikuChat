package com.example.adhit.bikubiku.ui.ruangBelajarChattingKabim;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.adhit.bikubiku.adapter.ChatRoomRuangBelajarKabimAdapter;
import com.example.adhit.bikubiku.data.model.ChatRoomHistory;
import com.example.adhit.bikubiku.presenter.ListRequestToKabimPresenter;
import com.example.adhit.bikubiku.presenter.RuangBelajarChattingKabimPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.listRequestToKabim.ListRequestToKabimActivity;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingHistory.RuangBelajarChattingHistoryFragment;
import com.example.adhit.bikubiku.util.CountDrawable;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by roy on 1/10/2018.
 */

public class RuangBelajarChattingKabimFragment extends Fragment implements RuangBelajarChattingKabimView {

    private RecyclerView rvChatRuangBelajar;
    private RuangBelajarChattingKabimPresenter ruangBelajarChattingKabimPresenter;
    private ChatRoomRuangBelajarKabimAdapter ruangBelajarChattingKabimAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Menu reqCountMenu;
    private final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Ruang Belajar");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_ruang_belajar_kabim, container, false);
        rvChatRuangBelajar = view.findViewById(R.id.rv_listChatBiquers);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        doTheAutoRefresh();
        initView();
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Subscribe
    public void getNewComment(QiscusCommentReceivedEvent event) {
        if (event.getQiscusComment() != null){
            ruangBelajarChattingKabimPresenter.getActiveChattingBiquers();
            rvChatRuangBelajar.setAdapter(ruangBelajarChattingKabimAdapter);
        }
    }

    private void doTheAutoRefresh() {
        handler.postDelayed(() -> {
//            ruangBelajarChattingKabimPresenter.getActiveChattingBiquers();
//            rvChatRuangBelajar.setAdapter(ruangBelajarChattingKabimAdapter);
            onRefreshMenu();
            doTheAutoRefresh();
        }, 1000);
    }

    private void initView() {
        ruangBelajarChattingKabimPresenter = new RuangBelajarChattingKabimPresenter(this);
        ruangBelajarChattingKabimPresenter.getActiveChattingBiquers();
        ruangBelajarChattingKabimAdapter = new ChatRoomRuangBelajarKabimAdapter(getActivity(), this);
        rvChatRuangBelajar.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatRuangBelajar.setAdapter(ruangBelajarChattingKabimAdapter);
    }

    @Override
    public void showData(List<ChatRoomHistory> chatRoomHistories) {
        ruangBelajarChattingKabimAdapter.setData(chatRoomHistories);
    }

    @Override
    public void onFailure(String s) {
        ShowAlert.showToast(getActivity(), s);
    }

    @Override
    public void onDetailChatRoomKabim(int idRoom) {
        ruangBelajarChattingKabimPresenter.openChatRoomKabimWithId(getActivity(), idRoom);
    }

    public void onRefreshMenu(){
        if (reqCountMenu != null){
            onPrepareOptionsMenu(reqCountMenu);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        String count = String.valueOf(SharedPrefUtil.getInt(ListRequestToKabimPresenter.TEMP_SIZE));
        setCount(getActivity(), count, menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_request_to_kabim, menu);
        this.reqCountMenu = menu;
    }

    public void setCount(Context context, String count, Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.ic_group);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
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
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    replace(R.id.frame_container,
                            new RuangBelajarChattingHistoryFragment(),
                            RuangBelajarChattingHistoryFragment.class.getSimpleName())
                    .addToBackStack(RuangBelajarChattingHistoryFragment.class.getSimpleName()).commit();
        }

        if (item.getItemId() == R.id.ic_group){
            Intent intent = new Intent(getActivity(), ListRequestToKabimActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
