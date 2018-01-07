package com.example.adhit.bikubiku.ui.home.home;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.HomeAdapter;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.presenter.HomePresenter;
import com.example.adhit.bikubiku.ui.psychologyconsultation.PsychologyConsultationFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView, HomeAdapter.OnDetailListener {

    private SliderLayout sliderLayout;
    private RecyclerView rvMenu;
    private HomeAdapter adapter;
    private HomePresenter presenter;

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
        presenter = new HomePresenter(this);
        presenter.showListHome();
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
    public void onItemDetailClicked(String menu) {
        if(menu.equals("Konsultasi Psikologi")){
            getFragmentManager().beginTransaction().
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    add(R.id.frame_container,
                            new PsychologyConsultationFragment(),
                            PsychologyConsultationFragment.class.getSimpleName())
                    .addToBackStack(PsychologyConsultationFragment.class.getSimpleName())
                    .commit();
        }
    }
}
