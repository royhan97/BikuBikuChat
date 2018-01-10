package com.example.adhit.bikubiku.ui.psychologychatting;

import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

/**
 * Created by adhit on 08/01/2018.
 */

public interface ChattingPsychologyView {
    void sendFirstMessage(QiscusComment comment);

    void canCreateRoom(boolean b);

    void openRoomChat(QiscusChatRoom qiscusChatRoom);

    void sendClosedMessage(QiscusComment comment);

    void showMessageClosedChatFromService(String success);
}
