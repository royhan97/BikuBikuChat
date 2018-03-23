package com.example.adhit.bikubiku.ui.detailpsychologist;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SaveUserData;
import com.example.adhit.bikubiku.data.model.PsychologistApprove;
import com.example.adhit.bikubiku.presenter.TransactionPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.loadingtransaction.LoadingTransactionActivity;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.ShowAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPsychologistFragment extends Fragment implements View.OnClickListener, TransactionView {


    private TextView tvPsychologistName, tvPsychologistPriceConsultation;
    private Button btnNext;
    private ImageView imgPsychologist;
    private Bundle bundle;
    private PsychologistApprove psychologistApprove;
    private TransactionPresenter transactionPresenter;
    public DetailPsychologistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Detail Psikolog");
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_detail_psychologist, container, false);
        tvPsychologistName = view.findViewById(R.id.tv_psychologist_name);
        tvPsychologistPriceConsultation = view.findViewById(R.id.tv_price_consultation);
        imgPsychologist = view.findViewById(R.id.img_psychologist);
        btnNext = view.findViewById(R.id.btn_next);
        initView();
        return view;
    }

    public void initView(){
        bundle = getArguments();
        psychologistApprove = bundle.getParcelable(Constant.PSYCHOLOGIST);
        tvPsychologistName.setText(psychologistApprove.getNama());
        tvPsychologistPriceConsultation.setText(psychologistApprove.getTarif());
        btnNext.setOnClickListener(this);
        transactionPresenter = new TransactionPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction().remove(this).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_next){
            SaveUserData.getInstance().savePsychologist(psychologistApprove.getNama());
           transactionPresenter.createTransaction(getActivity(), "psikologi", "1", "konsultasi", psychologistApprove.getId());
        }
    }

    @Override
    public void onFailure(String failed) {
        ShowAlert.showToast(getActivity(), failed);
    }

    @Override
    public void onSuccessMakeTransaction(String berhasil) {
        Intent intent = new Intent(getActivity(), LoadingTransactionActivity.class);
        intent.putExtra("pychologist_name", psychologistApprove.getNama());
        startActivity(intent);
        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction().remove(this).commit();

    }

    @Override
    public void onSuccessChangeTransactionStatus(String berhasil) {

    }
}
