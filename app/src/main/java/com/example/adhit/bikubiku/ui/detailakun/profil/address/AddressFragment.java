package com.example.adhit.bikubiku.ui.detailakun.profil.address;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.presenter.AddressPresenter;
import com.example.adhit.bikubiku.util.ShowAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment implements AddressView, View.OnClickListener {

    private EditText etAddress;
    private Button btnSave;
    private AddressPresenter addressPresenter;

    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        etAddress = view.findViewById(R.id.et_address);
        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        initView();
        return  view;

    }

    public void initView(){
        addressPresenter = new AddressPresenter(this);
        addressPresenter.getDataAddress();

    }

    @Override
    public void getDataAddress(String alamat) {
        etAddress.setText(alamat);
    }

    @Override
    public void showMessage(String string) {
        ShowAlert.showToast(getActivity(), string);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save){
            String address = etAddress.getText().toString().trim();
            if(address.isEmpty()){
                ShowAlert.showToast(getActivity(), getResources().getString(R.string.text_address_empty));
            }else{
                addressPresenter.changeDataAddress(getActivity(), address);
            }
        }
    }
}
