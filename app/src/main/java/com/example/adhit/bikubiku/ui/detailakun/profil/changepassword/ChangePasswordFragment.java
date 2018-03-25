package com.example.adhit.bikubiku.ui.detailakun.profil.changepassword;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.presenter.ChangePasswordPresenter;
import com.example.adhit.bikubiku.util.ShowAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements ChangePasswordView, View.OnClickListener {

    private EditText etOldPassword, etNewPassowrd, etNewPasswordConfirm;
    private Button btnSave;
    private ChangePasswordPresenter changePasswordPresenter;
    private CoordinatorLayout clChangePassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password_change, container, false);
        etOldPassword = view.findViewById(R.id.et_old_password);
        etNewPassowrd = view.findViewById(R.id.et_new_password);
        etNewPasswordConfirm = view.findViewById(R.id.et_new_password_confirm);
        btnSave = view.findViewById(R.id.btn_save);
        clChangePassword = view.findViewById(R.id.coordinatorLayout);
        initView();
        return view;
    }

    public void initView(){
        changePasswordPresenter = new ChangePasswordPresenter(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save){
            String oldPassword = etOldPassword.getText().toString().trim();
            String newPassword = etNewPassowrd.getText().toString().trim();
            String newPasswordConfirm = etNewPasswordConfirm.getText().toString().trim();
            if(oldPassword.isEmpty()){
                etOldPassword.setError(getResources().getString(R.string.text_cannot_empty));
                etOldPassword.requestFocus();
            }else if(newPassword.isEmpty()){
                etNewPassowrd.setError(getResources().getString(R.string.text_cannot_empty));
                etNewPassowrd.requestFocus();
            }else if(newPasswordConfirm.isEmpty()){
                etNewPasswordConfirm.setError(getResources().getString(R.string.text_cannot_empty));
                etNewPasswordConfirm.requestFocus();
            }else {
                ShowAlert.showProgresDialog(getActivity());
                changePasswordPresenter.changePassword( oldPassword, newPassword, newPasswordConfirm);
            }
        }
    }

    @Override
    public void onSuccessChangePassword(String message) {
        ShowAlert.showSnackBar(clChangePassword, message);
        ShowAlert.closeProgresDialog();
    }

    @Override
    public void onFailedChangePassword() {
        ShowAlert.showSnackBar(clChangePassword, getResources().getString(R.string.text_changed_failed));
        ShowAlert.closeProgresDialog();
    }
}
