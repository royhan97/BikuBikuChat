package com.example.adhit.bikubiku.ui.loadingtransaction;

import com.qiscus.sdk.data.model.QiscusChatRoom;

/**
 * Created by ASUS on 3/23/2018.
 */

public interface LoadingTransactionView {
    void onFailureOpenRoomChat(String s);

    void onSuccessOpenRoomChat(QiscusChatRoom qiscusChatRoom);
}
