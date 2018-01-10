package com.example.adhit.bikubiku.ui.listpsychologistchattinghistory;

import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * Created by adhit on 09/01/2018.
 */

public interface ListChattingPsychologistHistoryView {
    void showData(List<ChatRoomPsychologyHistory> carList);

    void onFailure(String s);

    void openRoomChat(QiscusChatRoom qiscusChatRoom);
}
