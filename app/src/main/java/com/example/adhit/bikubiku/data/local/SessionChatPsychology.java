package com.example.adhit.bikubiku.data.local;

import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

/**
 * Created by adhit on 08/01/2018.
 */

public class SessionChatPsychology {
    private static SessionChatPsychology ourInstance;

    private SessionChatPsychology() {
    }

    public static SessionChatPsychology getInstance() {
        if (ourInstance == null) ourInstance = new SessionChatPsychology();
        return ourInstance;
    }

    public boolean isRoomChatPsychologyConsultationBuild() {
        return SharedPrefUtil.getBoolean(Constant.IS_ROOM_CHAT_CONSULTATION_BUILD);
    }

    public void setRoomChatPsychologyConsultationIsBuild(boolean isbuild) {
        SharedPrefUtil.saveBoolean(Constant.IS_ROOM_CHAT_CONSULTATION_BUILD, isbuild);
    }

}
