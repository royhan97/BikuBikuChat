package com.example.adhit.bikubiku.ui.listpsychologistchattinghistory;

import com.example.adhit.bikubiku.data.model.ChatRoomPsychologyHistory;

import java.util.List;

/**
 * Created by adhit on 09/01/2018.
 */

public interface ListChattingPsychologistHistoryView {
    void showData(List<ChatRoomPsychologyHistory> carList);

    void onFailure(String s);
}
