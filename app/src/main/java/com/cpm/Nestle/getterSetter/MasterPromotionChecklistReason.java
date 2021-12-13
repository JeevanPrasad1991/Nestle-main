package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yadavendras on 26-08-2016.
 */
public class MasterPromotionChecklistReason {
    @SerializedName("ReasonId")
    @Expose
    private int reasonId;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("ChecklistId")
    @Expose
    private int checklistId;
    @SerializedName("ImageAllow")
    @Expose
    private boolean imageAllow;

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public boolean isImageAllow() {
        return imageAllow;
    }

    public void setImageAllow(boolean imageAllow) {
        this.imageAllow = imageAllow;
    }
}
