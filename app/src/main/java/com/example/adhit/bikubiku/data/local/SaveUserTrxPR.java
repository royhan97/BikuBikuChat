package com.example.adhit.bikubiku.data.local;

import com.example.adhit.bikubiku.data.model.TransactionPR;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

/**
 * Created by roy on 1/24/2018.
 */

public class SaveUserTrxPR {
    private static SaveUserTrxPR ourInstance;

    private SaveUserTrxPR() {
    }

    public static SaveUserTrxPR getInstance() {
        if (ourInstance == null) ourInstance = new SaveUserTrxPR();
        return ourInstance;
    }

    public TransactionPR getTrx() {
        return (TransactionPR) SharedPrefUtil.getObject(Constant.KEY_TRX, TransactionPR.class);
    }

    public void saveTrx(TransactionPR trx) {
        SharedPrefUtil.saveObject(Constant.KEY_TRX, trx);
    }

    public void removeTrx(){
        SharedPrefUtil.remove(Constant.KEY_TRX);
    }

    public void saveEndTimeTrx(Long time){
        SharedPrefUtil.saveLong(Constant.END_TIME_OF_TRANSACTION, time);
    }

    public long getEndTimeTrx(){
        return SharedPrefUtil.getLong(Constant.END_TIME_OF_TRANSACTION);
    }

    public void removeEndTimeTrx(){
        SharedPrefUtil.remove(Constant.END_TIME_OF_TRANSACTION);
    }
}
