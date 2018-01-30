package com.example.adhit.bikubiku.data.local;

import com.example.adhit.bikubiku.data.model.TransaksiPR;
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

    public TransaksiPR getTrx() {
        return (TransaksiPR) SharedPrefUtil.getObject(Constant.KEY_TRX, TransaksiPR.class);
    }

    public void saveTrx(TransaksiPR trx) {
        SharedPrefUtil.saveObject(Constant.KEY_TRX, trx);
    }

    public void removeTrx(){
        SharedPrefUtil.remove(Constant.KEY_TRX);
    }
}
