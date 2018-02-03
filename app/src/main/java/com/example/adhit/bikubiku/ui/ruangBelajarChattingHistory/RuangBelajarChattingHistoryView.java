package com.example.adhit.bikubiku.ui.ruangBelajarChattingHistory;

import com.example.adhit.bikubiku.data.model.ChatRoomHistory;
import com.example.adhit.bikubiku.data.model.RequestToKabim;

import java.util.List;

/**
 * Created by roy on 1/10/2018.
 */

public interface RuangBelajarChattingHistoryView {

    void showData(List<ChatRoomHistory> chatRoomHistories);

    void onFailure(String s);

    void onDetailChatRoomHistory(int idRoom);

}
