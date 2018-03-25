package com.example.adhit.bikubiku.ui.sakubiku;

import com.example.adhit.bikubiku.data.model.AccountBank;
import com.example.adhit.bikubiku.data.model.SakuBikuMenu;

import java.util.ArrayList;

/**
 * Created by ASUS on 3/24/2018.
 */

public interface SakuBikuView {
    void showData(ArrayList<SakuBikuMenu> sakuBikuMenuArrayList);

    void onFailedShowSaldo(String error);

    void onSuccessTopupBalance();

    void onFailedTopupBalance(String message);

    void onFailedCashOutBalance(String s);

    void onSuccessCashOutBalance();

    void onSuccessShowSaldo(String balance, String topup, String cashout);

    void onSuccessAddAccountBank(AccountBank accountBank);

    void onFailedAddAccountBank(String message);

    void onFailedGetAccountBank(String s);

    void onSuccessGetAccountBank(AccountBank accountBank);
}
