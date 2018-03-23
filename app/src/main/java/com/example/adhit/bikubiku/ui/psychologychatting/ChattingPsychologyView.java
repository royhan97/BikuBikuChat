package com.example.adhit.bikubiku.ui.psychologychatting;

import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

/**
 * Created by adhit on 08/01/2018.
 */

public interface ChattingPsychologyView {

    void canCreateRoom(boolean b);

    void openRoomChat(QiscusChatRoom qiscusChatRoom);

    void sendClosedMessage(QiscusComment comment);

    void onFailure(String failed);

    void onFinishTransaction();
}
