package com.example.adhit.bikubiku.data.model;

import java.util.ArrayList;

/**
 * Created by adhit on 29/12/2017.
 */

public class RoomChat {
    private int roomId;
    private int unixTimeStamp;
    private String roomName;
    private String roomAvatarUrl;
    private String lastMessage;
    private ArrayList<ParticipantRoomChat> participantRoomChatArrayList;

    public RoomChat(int roomId, int unixTimeStamp, String roomName, String roomAvatarUrl, String lastMessage, ArrayList<ParticipantRoomChat> participantRoomChatArrayList) {
        this.roomId = roomId;
        this.unixTimeStamp = unixTimeStamp;
        this.roomName = roomName;
        this.roomAvatarUrl = roomAvatarUrl;
        this.lastMessage = lastMessage;
        this.participantRoomChatArrayList = participantRoomChatArrayList;
    }



    public int getUnixTimeStamp() {
        return unixTimeStamp;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomAvatarUrl() {
        return roomAvatarUrl;
    }

    public ArrayList<ParticipantRoomChat> getParticipantRoomChatArrayList() {
        return participantRoomChatArrayList;
    }
}
