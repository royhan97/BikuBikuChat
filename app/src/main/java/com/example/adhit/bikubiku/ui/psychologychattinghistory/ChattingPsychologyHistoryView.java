package com.example.adhit.bikubiku.ui.psychologychattinghistory;

import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;

import java.util.List;

/**
 * Created by adhit on 09/01/2018.
 */

public interface ChattingPsychologyHistoryView {
    void showData(List<ChatRoomPsychologyHistory> carList);

    void onFailure(String s);
}
