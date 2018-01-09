package com.example.adhit.bikubiku.data.model;

/**
 * Created by adhit on 09/01/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRoomPsychologyParticipant {

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_str")
    @Expose
    private String idStr;
    @SerializedName("last_comment_read_id")
    @Expose
    private Integer lastCommentReadId;
    @SerializedName("last_comment_read_id_str")
    @Expose
    private String lastCommentReadIdStr;
    @SerializedName("last_comment_received_id")
    @Expose
    private Integer lastCommentReceivedId;
    @SerializedName("last_comment_received_id_str")
    @Expose
    private String lastCommentReceivedIdStr;
    @SerializedName("username")
    @Expose
    private String username;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Integer getLastCommentReadId() {
        return lastCommentReadId;
    }

    public void setLastCommentReadId(Integer lastCommentReadId) {
        this.lastCommentReadId = lastCommentReadId;
    }

    public String getLastCommentReadIdStr() {
        return lastCommentReadIdStr;
    }

    public void setLastCommentReadIdStr(String lastCommentReadIdStr) {
        this.lastCommentReadIdStr = lastCommentReadIdStr;
    }

    public Integer getLastCommentReceivedId() {
        return lastCommentReceivedId;
    }

    public void setLastCommentReceivedId(Integer lastCommentReceivedId) {
        this.lastCommentReceivedId = lastCommentReceivedId;
    }

    public String getLastCommentReceivedIdStr() {
        return lastCommentReceivedIdStr;
    }

    public void setLastCommentReceivedIdStr(String lastCommentReceivedIdStr) {
        this.lastCommentReceivedIdStr = lastCommentReceivedIdStr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}