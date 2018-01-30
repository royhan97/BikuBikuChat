package com.example.adhit.bikubiku.ui.ruangBelajarChatting;

import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;

import java.util.Date;

/**
 * Created by roy on 1/4/2018.
 */

public interface RuangBelajarView {

    void setupCustomChatConfig();

    void sendClosedMessage(QiscusComment comment);

    void openChatRoom(QiscusChatRoom qiscusChatRoom);

    void showMessageClosedChatFromService(String status);

    void onUserOnline(String user, boolean isOnline, Date lastActive);

    void openWaitingActivity();

    void setIdRoom(int idRoom);
}
