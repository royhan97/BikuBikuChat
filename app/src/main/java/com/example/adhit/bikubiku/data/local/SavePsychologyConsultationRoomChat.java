package com.example.adhit.bikubiku.data.local;

import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.util.Constant;
import com.example.adhit.bikubiku.util.SharedPrefUtil;

/**
 * Created by adhit on 08/01/2018.
 */

public class SavePsychologyConsultationRoomChat {

    private static SavePsychologyConsultationRoomChat ourInstance;

    private SavePsychologyConsultationRoomChat() {
    }

    public static SavePsychologyConsultationRoomChat getInstance() {
        if (ourInstance == null) ourInstance = new SavePsychologyConsultationRoomChat();
        return ourInstance;
    }

    public int getPsychologyConsultationRoomChat() {
        return  SharedPrefUtil.getInt(Constant.PSYCHOLOGIST_ROOM_CHAT);
    }

    public void savePsychologyConsultationRoomChat(int idRoom) {
        SharedPrefUtil.saveInt(Constant.PSYCHOLOGIST_ROOM_CHAT, idRoom);
    }

    public void removePsychologyConsultationRoomChat(){
        SharedPrefUtil.remove(Constant.PSYCHOLOGIST_ROOM_CHAT);
    }


}
