package com.example.adhit.bikubiku.data.model;


/**
 * Created by adhit on 07/01/2018.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Psychologist{

    @SerializedName("approve")
    @Expose
    private List<PsychologistApprove> approve = null;
    @SerializedName("unapprove")
    @Expose
    private List<PsychologistUnapprove> unapprove = null;

    public List<PsychologistApprove> getApprove() {
        return approve;
    }

    public void setApprove(List<PsychologistApprove> approve) {
        this.approve = approve;
    }

    public List<PsychologistUnapprove> getUnapprove() {
        return unapprove;
    }

    public void setUnapprove(List<PsychologistUnapprove> unapprove) {
        this.unapprove = unapprove;
    }

}