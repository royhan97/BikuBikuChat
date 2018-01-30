package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 29/12/2017.
 */

public class ParticipantRoomChat {
    private String avatarUrlParticipant;
    private String emailParticipant;
    private String userNameParticipant;

    public ParticipantRoomChat(String avatarUrlParticipant, String emailParticipant, String userNameParticipant) {
        this.avatarUrlParticipant = avatarUrlParticipant;
        this.emailParticipant = emailParticipant;
        this.userNameParticipant = userNameParticipant;
    }

    public String getAvatarUrlParticipant() {
        return avatarUrlParticipant;
    }

    public String getEmailParticipant() {
        return emailParticipant;
    }

    public String getUserNameParticipant() {
        return userNameParticipant;
    }
}
