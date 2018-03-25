package com.example.adhit.bikubiku.ui.sakubiku;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.HomeAdapter;
import com.example.adhit.bikubiku.adapter.SakuBikuMenuAdapter;
import com.example.adhit.bikubiku.data.model.AccountBank;
import com.example.adhit.bikubiku.data.model.SakuBikuMenu;
import com.example.adhit.bikubiku.presenter.HomePresenter;
import com.example.adhit.bikubiku.presenter.SakuBikuPresenter;
import com.example.adhit.bikubiku.ui.home.HomeActivity;
import com.example.adhit.bikubiku.ui.home.home.HomeFragment;
import com.example.adhit.bikubiku.ui.login.LoginActivity;
import com.example.adhit.bikubiku.util.ShowAlert;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SakuBikuFragment extends Fragment implements SakuBikuMenuAdapter.OnDetailListener, SakuBikuView {

    private SakuBikuMenuAdapter sakuBikuMenuAdapter;
    private SakuBikuPresenter sakuBikuPresenter;
    private TextView tvSaldo, tvTopup, tvCashOut, tvBankName, tvAccountBankName;
    private RecyclerView rvSakuBikuMenu;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private CoordinatorLayout clSakuBiku;
    private String aimBank, bank;


    public SakuBikuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.img_logo).setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Saku Biku");
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_saku_biku, container, false);
        rvSakuBikuMenu = view.findViewById(R.id.rv_menu_sakubiku);
        tvSaldo = view.findViewById(R.id.tv_sakubiku_balance);
        tvTopup = view.findViewById(R.id.tv_topup);
        tvCashOut = view.findViewById(R.id.tv_cashout);
        tvBankName = view.findViewById(R.id.tv_bankname_bankaccountnumber);
        tvAccountBankName = view.findViewById(R.id.tv_accountbankname);
        clSakuBiku = view.findViewById(R.id.cl_sakubiku);
        initView();
        return view;
    }

    public void initView(){
        ShowAlert.showProgresDialog(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        sakuBikuMenuAdapter = new SakuBikuMenuAdapter(getActivity());
        sakuBikuMenuAdapter.setOnClickDetailListener(this);
        rvSakuBikuMenu.setAdapter(sakuBikuMenuAdapter);
        rvSakuBikuMenu.setHasFixedSize(true);
        rvSakuBikuMenu.setLayoutManager(gridLayoutManager);
        sakuBikuPresenter = new SakuBikuPresenter(this);
        sakuBikuPresenter.showListSakuBikuMenu();
        sakuBikuPresenter.showSaldo();
        sakuBikuPresenter.getAccountBankData();
    }


    @Override
    public void onItemDetailClicked(String menu) {
        if(menu.equals("Top Up")){
            alertDialogTopUp();
        }else if(menu.equals("Tarik Dana")){
           alertDialogCashOut();
        }else if(menu.equals("Tambah Rekening")){
            alertDialogAccountManagement();
        }
    }


    @Override
    public void showData(ArrayList<SakuBikuMenu> sakuBikuMenuArrayList) {
        sakuBikuMenuAdapter.setData(sakuBikuMenuArrayList);
    }



    @Override
    public void onFailedShowSaldo(String error) {
        tvSaldo.setText(error);
        tvTopup.setText(error);
        tvCashOut.setText(error);
    }

    @Override
    public void onSuccessTopupBalance() {
        alertDialog.dismiss();
        ShowAlert.closeProgresDialog();
        sakuBikuPresenter.showSaldo();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getResources().getString(R.string.text_success_topup));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onFailedTopupBalance(String message) {
        alertDialog.dismiss();
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clSakuBiku, message);
    }

    @Override
    public void onFailedCashOutBalance(String s) {
        alertDialog.dismiss();
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clSakuBiku, s);
    }

    @Override
    public void onSuccessCashOutBalance() {
        alertDialog.dismiss();
        ShowAlert.closeProgresDialog();
        sakuBikuPresenter.showSaldo();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getResources().getString(R.string.text_success_cashout));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onSuccessShowSaldo(String balance, String topup, String cashout) {
        tvSaldo.setText("Rp "+balance);
        tvTopup.setText("Top Up : "+ topup.toString());
        tvCashOut.setText("Cash out : "+ cashout);
    }

    @Override
    public void onSuccessAddAccountBank(AccountBank accountBank) {
        ShowAlert.closeProgresDialog();
        alertDialog.dismiss();
        tvBankName.setText("Bank : "+ accountBank.getBank() +" ~ "+ accountBank.getRekening());
        tvAccountBankName.setText("Atas Nama : " +accountBank.getAtasNama());
    }

    @Override
    public void onFailedAddAccountBank(String message) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clSakuBiku, message);
    }

    @Override
    public void onFailedGetAccountBank(String s) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showSnackBar(clSakuBiku, s);
    }

    @Override
    public void onSuccessGetAccountBank(AccountBank accountBank) {
        ShowAlert.closeProgresDialog();
        tvBankName.setText("Bank : "+ accountBank.getBank() +" ~ "+ accountBank.getRekening());
        tvAccountBankName.setText("Atas Nama : " +accountBank.getAtasNama());
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

    private void alertDialogTopUp(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_topup, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        EditText etBalance = dialogView.findViewById(R.id.et_balance);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnTopUp = dialogView.findViewById(R.id.btn_topup);
        Spinner spnAimBank = dialogView.findViewById(R.id.spn_aim_bank);

        ArrayList aimBankList = new ArrayList<>();
        aimBankList.add("BTN ~ 00175-01-61-018017-2 atas nama ADITIA PRASETIO");
        aimBankList.add("BRI ~ 0515-01-014535-50-9 atas nama ADITIA PRASETIO ");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, aimBankList);
        spnAimBank.setAdapter(adapter);
        spnAimBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                aimBank = (String) aimBankList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCancel.setOnClickListener(view -> alertDialog.dismiss());

        btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String balance = etBalance.getText().toString().trim();
                if(aimBank.isEmpty()){
                    ShowAlert.showToast(getActivity(), "Anda belum memilih bank tujuan");
                }else if(balance.isEmpty()){
                    etBalance.setError(getResources().getString(R.string.text_cannot_empty));
                    etBalance.requestFocus();
                }else{
                    ShowAlert.showProgresDialog(getActivity());
                    sakuBikuPresenter.topupBalacne(aimBank, balance);
                }
            }
        });
        alertDialog.show();
    }
    private void alertDialogAccountManagement(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_account_bank_management, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        EditText etAccountBankName = dialogView.findViewById(R.id.et_account_bank_name);
        EditText etAccountBankNumber = dialogView.findViewById(R.id.et_account_bank_number_name);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        Spinner spnBank = dialogView.findViewById(R.id.spn_bank);

        ArrayList bankList = new ArrayList<>();
        bankList.add("BRI");
        bankList.add("BNI");
        bankList.add("BTN");
        bankList.add("BCA");
        bankList.add("Mandiri");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, bankList);
        spnBank.setAdapter(adapter);
        spnBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank = (String) bankList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCancel.setOnClickListener(view -> alertDialog.dismiss());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountBankName = etAccountBankName.getText().toString().trim();
                String accountBankNumberName = etAccountBankNumber.getText().toString().trim();
                if(accountBankName.isEmpty()){
                    etAccountBankName.setError(getResources().getString(R.string.text_cannot_empty));
                    etAccountBankName.requestFocus();
                }else if(accountBankNumberName.isEmpty()){
                    etAccountBankNumber.setError(getResources().getString(R.string.text_cannot_empty));
                    etAccountBankNumber.requestFocus();
                }else{
                    ShowAlert.showProgresDialog(getActivity());
                    sakuBikuPresenter.accountBankManagement(bank, accountBankName, accountBankNumberName);
                }
            }
        });
        alertDialog.show();
    }

    private void alertDialogCashOut(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_cashout, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        EditText etBalance = dialogView.findViewById(R.id.et_balance);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnCashOut = dialogView.findViewById(R.id.btn_cashout);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnCashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String balance = etBalance.getText().toString().trim();
                if(balance.isEmpty()){
                    etBalance.setError(getResources().getString(R.string.text_cannot_empty));
                    etBalance.requestFocus();
                }else{
                    ShowAlert.showProgresDialog(getActivity());
                    sakuBikuPresenter.cashoutBalacne(balance);
                }
            }
        });
        alertDialog.show();
    }
}
