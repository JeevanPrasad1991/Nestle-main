package com.cpm.Nestle.getterSetter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterNonPromotion {

    @SerializedName("ReasonId")
    @Expose
    private Integer reasonId;
    @SerializedName("Reason")
    @Expose
    private String reason;

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public MasterNonPromotion withReasonId(Integer reasonId) {
        this.reasonId = reasonId;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public MasterNonPromotion withReason(String reason) {
        this.reason = reason;
        return this;
    }

}
