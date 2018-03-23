package com.example.adhit.bikubiku.ui.listpsychologist;

import com.example.adhit.bikubiku.data.model.PsychologistApprove;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.List;

/**
 * Created by adhit on 07/01/2018.
 */

public interface ListPsychologistView {
    void showData(List<PsychologistApprove> psychologistArrayList);

    void showBlock(boolean roomChatPsychologyConsultationBuild);

    void onFailure(String s);

    void onSuccessOpenRoomChat(QiscusChatRoom qiscusChatRoom);

    void onFailedOpenRoomChat(String s);
}
