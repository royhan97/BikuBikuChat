package com.example.adhit.bikubiku.ui.psychologyconsultation;

import com.example.adhit.bikubiku.data.model.Psychologist;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.ArrayList;

/**
 * Created by adhit on 07/01/2018.
 */

public interface PsychologyConsultationView {
    void showData(ArrayList<Psychologist> psychologistArrayList);

    void showBlock(boolean roomChatPsychologyConsultationBuild);

    void openRoomChat(QiscusChatRoom qiscusChatRoom);
}
