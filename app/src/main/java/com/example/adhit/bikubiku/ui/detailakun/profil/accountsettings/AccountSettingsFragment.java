package com.example.adhit.bikubiku.ui.detailakun.profil.accountsettings;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.presenter.AccountSettingsPresenter;
import com.example.adhit.bikubiku.util.ShowAlert;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountSettingsFragment extends Fragment implements AccountSettingsView, View.OnClickListener {

    private EditText etName, etUsername, etEmail, etWa, etIdLine, etBio;
    private Spinner spnAim;
    private AccountSettingsPresenter accountSettingsPresenter;
    private Button btnSave;
    private ArrayList aimString;
    public String aim;
    private CoordinatorLayout clAccountSettings;

    public AccountSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        etName = view.findViewById(R.id.et_name);
        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etWa = view.findViewById(R.id.et_wa);
        etIdLine = view.findViewById(R.id.et_id_line);
        etBio = view.findViewById(R.id.et_bio);
        spnAim = view.findViewById(R.id.spn_aim);
        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        clAccountSettings = view.findViewById(R.id.cl_account_settings);
        initSpinner();
        initPresenter();
        return  view;
    }

    private void initPresenter() {
        accountSettingsPresenter = new AccountSettingsPresenter(this);
        accountSettingsPresenter.getDataAccount();
    }

    private void initSpinner(){

        aimString = new ArrayList<>();
        aimString.add("Saya hanya ingin belajar");
        aimString.add("Saya hanya ingin mengajar");
        aimString.add("Saya ingin belajar sekaligus mengajar");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, aimString);
        spnAim.setAdapter(adapter);
        spnAim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                aim = (String) aimString.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void getDataAccount(User user) {
        etName.setText(user.getNama());
        etUsername.setText(user.getUsername());
        etEmail.setText(user.getEmail());
        etWa.setText(user.getWa());
        etIdLine.setText(user.getIdLine());
        etBio.setText(user.getBio());
    }

    @Override
    public void onFailedChangeDataAccount(String s) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clAccountSettings, s);
    }

    @Override
    public void onSuccessChangeDataAccount(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clAccountSettings, message);
        accountSettingsPresenter.getDataAccount();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save){
            String name = etName.getText().toString().trim();
            String userName =etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String wa = etWa.getText().toString().trim();
            String idLine = etIdLine.getText().toString().trim();
            String bio = etBio.getText().toString().trim();
            if(name.isEmpty()){
                etName.setError(getResources().getString(R.string.text_cannot_empty));
                etName.requestFocus();
            }else  if(userName.isEmpty()){
                etUsername.setError(getResources().getString(R.string.text_cannot_empty));
                etUsername.requestFocus();
            }else if(email.isEmpty()){
                etEmail.setError(getResources().getString(R.string.text_cannot_empty));
                etEmail.requestFocus();
            }else if(wa.isEmpty()){
                etWa.setError(getResources().getString(R.string.text_cannot_empty));
                etWa.requestFocus();
            }else if(idLine.isEmpty()){
                etIdLine.setError(getResources().getString(R.string.text_cannot_empty));
                etIdLine.requestFocus();
            }else if(bio.isEmpty()){
                etBio.setError(getResources().getString(R.string.text_cannot_empty));
                etBio.requestFocus();
            }else if(aim.isEmpty()){
                ShowAlert.showToast(getActivity(), "Anda Belum memilih tujuan");
            }else {
                ShowAlert.showProgresDialog(getActivity());
                accountSettingsPresenter.changeDataAccount(name, userName, email, aim, wa, idLine, bio);
            }
        }
    }
}
