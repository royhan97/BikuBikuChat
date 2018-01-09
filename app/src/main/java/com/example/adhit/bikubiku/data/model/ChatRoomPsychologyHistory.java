package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 09/01/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRoomPsychologyHistory {

    @SerializedName("is_removed")
    @Expose
    private Boolean isRemoved;
    @SerializedName("last_comment_id")
    @Expose
    private Integer lastCommentId;
    @SerializedName("last_comment_id_str")
    @Expose
    private String lastCommentIdStr;
    @SerializedName("last_comment_message")
    @Expose
    private String lastCommentMessage;
    @SerializedName("last_comment_sender_email")
    @Expose
    private String lastCommentSenderEmail;
    @SerializedName("last_comment_sender_id")
    @Expose
    private Integer lastCommentSenderId;
    @SerializedName("last_comment_sender_id_str")
    @Expose
    private String lastCommentSenderIdStr;
    @SerializedName("last_comment_sender_username")
    @Expose
    private String lastCommentSenderUsername;
    @SerializedName("last_comment_timestamp")
    @Expose
    private String lastCommentTimestamp;
    @SerializedName("last_comment_timestamp_unix")
    @Expose
    private Integer lastCommentTimestampUnix;
    @SerializedName("participants")
    @Expose
    private List<ChatRoomPsychologyParticipant> participants = null;
    @SerializedName("raw_room_name")
    @Expose
    private String rawRoomName;
    @SerializedName("room_avatar_url")
    @Expose
    private String roomAvatarUrl;
    @SerializedName("room_id")
    @Expose
    private Integer roomId;
    @SerializedName("room_id_str")
    @Expose
    private String roomIdStr;
    @SerializedName("room_name")
    @Expose
    private String roomName;
    @SerializedName("room_options")
    @Expose
    private String roomOptions;
    @SerializedName("room_type")
    @Expose
    private String roomType;
    @SerializedName("room_unique_id")
    @Expose
    private String roomUniqueId;
    @SerializedName("unread_count")
    @Expose
    private Integer unreadCount;

    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Integer getLastCommentId() {
        return lastCommentId;
    }

    public void setLastCommentId(Integer lastCommentId) {
        this.lastCommentId = lastCommentId;
    }

    public String getLastCommentIdStr() {
        return lastCommentIdStr;
    }

    public void setLastCommentIdStr(String lastCommentIdStr) {
        this.lastCommentIdStr = lastCommentIdStr;
    }

    public String getLastCommentMessage() {
        return lastCommentMessage;
    }

    public void setLastCommentMessage(String lastCommentMessage) {
        this.lastCommentMessage = lastCommentMessage;
    }

    public String getLastCommentSenderEmail() {
        return lastCommentSenderEmail;
    }

    public void setLastCommentSenderEmail(String lastCommentSenderEmail) {
        this.lastCommentSenderEmail = lastCommentSenderEmail;
    }

    public Integer getLastCommentSenderId() {
        return lastCommentSenderId;
    }

    public void setLastCommentSenderId(Integer lastCommentSenderId) {
        this.lastCommentSenderId = lastCommentSenderId;
    }

    public String getLastCommentSenderIdStr() {
        return lastCommentSenderIdStr;
    }

    public void setLastCommentSenderIdStr(String lastCommentSenderIdStr) {
        this.lastCommentSenderIdStr = lastCommentSenderIdStr;
    }

    public String getLastCommentSenderUsername() {
        return lastCommentSenderUsername;
    }

    public void setLastCommentSenderUsername(String lastCommentSenderUsername) {
        this.lastCommentSenderUsername = lastCommentSenderUsername;
    }

    public String getLastCommentTimestamp() {
        return lastCommentTimestamp;
    }

    public void setLastCommentTimestamp(String lastCommentTimestamp) {
        this.lastCommentTimestamp = lastCommentTimestamp;
    }

    public Integer getLastCommentTimestampUnix() {
        return lastCommentTimestampUnix;
    }

    public void setLastCommentTimestampUnix(Integer lastCommentTimestampUnix) {
        this.lastCommentTimestampUnix = lastCommentTimestampUnix;
    }

    public List<ChatRoomPsychologyParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ChatRoomPsychologyParticipant> participants) {
        this.participants = participants;
    }

    public String getRawRoomName() {
        return rawRoomName;
    }

    public void setRawRoomName(String rawRoomName) {
        this.rawRoomName = rawRoomName;
    }

    public String getRoomAvatarUrl() {
        return roomAvatarUrl;
    }

    public void setRoomAvatarUrl(String roomAvatarUrl) {
        this.roomAvatarUrl = roomAvatarUrl;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomIdStr() {
        return roomIdStr;
    }

    public void setRoomIdStr(String roomIdStr) {
        this.roomIdStr = roomIdStr;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomOptions() {
        return roomOptions;
    }

    public void setRoomOptions(String roomOptions) {
        this.roomOptions = roomOptions;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomUniqueId() {
        return roomUniqueId;
    }

    public void setRoomUniqueId(String roomUniqueId) {
        this.roomUniqueId = roomUniqueId;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

}
