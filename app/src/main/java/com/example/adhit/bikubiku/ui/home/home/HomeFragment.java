package com.example.adhit.bikubiku.ui.home.home;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.HomeAdapter;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.presenter.HomePresenter;
import com.example.adhit.bikubiku.ui.home.akun.AkunFragment;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyFragment;
import com.example.adhit.bikubiku.ui.listKabim.ListKabimFragment;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingHistory.RuangBelajarChattingHistoryFragment;
import com.example.adhit.bikubiku.ui.ruangBelajarChattingKabim.RuangBelajarChattingKabimFragment;
import com.example.adhit.bikubiku.ui.sakubiku.SakuBikuFragment;
import com.example.adhit.bikubiku.ui.tesminat.TesMinatActivity;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;
import com.example.adhit.bikubiku.ui.listpsychologistchattinghistory.ListChattingPsychologistHistoryFragment;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.example.adhit.bikubiku.ui.listpsychologist.ListPsychologistFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView, HomeAdapter.OnDetailListener, View.OnClickListener {

    private SliderLayout sliderLayout;
    private RecyclerView rvMenu;
    private HomeAdapter adapter;
    private HomePresenter presenter;
    private TextView tvSaldo, tvName;
    private LinearLayout llProfileName, llSakuBiku;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderLayout = view.findViewById(R.id.slider);
        rvMenu = view.findViewById(R.id.rv_menu);
        tvSaldo = view.findViewById(R.id.tv_saldo);
        tvName = view.findViewById(R.id.tv_name);
        llProfileName = view.findViewById(R.id.ll_profil);
        llSakuBiku = view.findViewById(R.id.ll_sakubiku);
        init();
        initView();
        return view;
    }

    public void initView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        adapter = new HomeAdapter(getActivity());
        adapter.setOnClickDetailListener(this);
        rvMenu.setAdapter(adapter);
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(gridLayoutManager);
        presenter = new HomePresenter(getActivity(),this);
        presenter.showListHome();
        presenter.showSaldo();
        presenter.showName();
        llProfileName.setOnClickListener(this);
        llSakuBiku.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.showSaldo();
        presenter.showName();
    }

    public void init(){
        ArrayList<Integer> gambar = new ArrayList<>();
        gambar.add(R.drawable.a1);
        gambar.add(R.drawable.a2);
        gambar.add(R.drawable.a2);
        for(int i=0; i<gambar.size(); i++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(gambar.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderLayout.addSlider(textSliderView);

        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }

    @Override
    public void showData(ArrayList<Home> homeArrayList) {
        adapter.setData(homeArrayList);
    }


    @Override
    public void showError(String s) {
        ShowAlert.showToast(getActivity(), s);
    }

    @Override
    public void onFailedShowSaldo(String error) {
        tvSaldo.setText(error);
    }

    @Override
    public void onSuccessShowSaldo(String s) {
        tvSaldo.setText(s);
    }

    @Override
    public void onSuccessShowName(String s) {
        tvName.setText(s);
    }

    @Override
    public void onItemDetailClicked(String menu) {
        if(menu.equals("Konsultasi Psikologi")){
            getFragmentManager().beginTransaction().
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    replace(R.id.frame_container,
                            new ListPsychologistFragment(),
                            ListPsychologistFragment.class.getSimpleName())
                    .addToBackStack(ListPsychologistFragment.class.getSimpleName())
                    .commit();
        }
        else if(menu.equals("History Konsultasi")){
            getFragmentManager().beginTransaction().
                    replace(R.id.frame_container,
                            new ListChattingPsychologistHistoryFragment(),
                            ChattingPsychologyFragment.class.getSimpleName())
                    .addToBackStack(ListChattingPsychologistHistoryFragment.class.getSimpleName())
                    .commit();
        }

        else if(menu.equals("Ruang Belajar")){
            if (SharedPrefUtil.getBoolean(Constant.IS_LOGIN_KABIM)){
                getFragmentManager().beginTransaction().
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                        replace(R.id.frame_container,
                                new RuangBelajarChattingKabimFragment(),
                                RuangBelajarChattingKabimFragment.class.getSimpleName())
                        .addToBackStack(RuangBelajarChattingKabimFragment.class.getSimpleName()).commit();
            }
            else {
                getFragmentManager().beginTransaction().
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                        replace(R.id.frame_container,
                                new ListKabimFragment(),
                                ListKabimFragment.class.getSimpleName())
                        .addToBackStack(ListKabimFragment.class.getSimpleName()).commit();
            }
        } else if(menu.equals("Tes Minat")){
            Intent intent = new Intent(getActivity(), TesMinatActivity.class);
            startActivity(intent);

        }else{
            ShowAlert.showToast(getActivity(), "Coming Soon");
        }


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ll_profil){
                getFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new AkunFragment(),
                        AkunFragment.class.getSimpleName())
                        .addToBackStack(AkunFragment.class.getSimpleName())
                .commit();
        }
        if(view.getId() == R.id.ll_sakubiku){
            getFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new SakuBikuFragment(),
                    SakuBikuFragment.class.getSimpleName())
                    .addToBackStack(SakuBikuFragment.class.getSimpleName())
                    .commit();
        }
    }
}
