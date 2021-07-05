package com.cpm.Nestle.getterSetter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterNonPromotionReason {

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
