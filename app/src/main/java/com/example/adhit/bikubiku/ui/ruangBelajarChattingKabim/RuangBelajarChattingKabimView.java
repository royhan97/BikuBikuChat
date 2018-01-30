package com.example.adhit.bikubiku.ui.ruangBelajarChattingKabim;

import com.example.adhit.bikubiku.data.model.ChatRoomHistory;

import java.time.Instant;
import java.util.List;

/**
 * Created by roy on 1/10/2018.
 */

public interface RuangBelajarChattingKabimView {

    void showData(List<ChatRoomHistory> chatRoomHistories);

    void onFailure(String s);

    void onDetailChatRoomKabim(int idRoom);
}
