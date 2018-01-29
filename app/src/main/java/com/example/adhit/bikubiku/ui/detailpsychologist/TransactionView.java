package com.example.adhit.bikubiku.ui.detailpsychologist;

/**
 * Created by adhit on 18/01/2018.
 */

public interface TransactionView {
    void onFailure(String message);


    void onSuccessMakeTransaction(String berhasil);

    void onSuccessChangeTransactionStatus(String berhasil);
}
