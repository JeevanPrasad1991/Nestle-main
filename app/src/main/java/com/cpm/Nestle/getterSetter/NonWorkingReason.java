
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonWorkingReason {

    @SerializedName("ReasonId")
    @Expose
    private Integer reasonId;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("EntryAllow")
    @Expose
    private Boolean entryAllow;
    @SerializedName("ImageAllow")
    @Expose
    private Boolean imageAllow;

    @SerializedName("GPSMandatory")
    @Expose
    private Boolean gPSMandatory;

    public Boolean getPosmTracking() {
        return PosmTracking;
    }

    public void setPosmTracking(Boolean posmTracking) {
        PosmTracking = posmTracking;
    }

    @SerializedName("PosmTracking")
    @Expose
    private Boolean PosmTracking;

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

    public Boolean getEntryAllow() {
        return entryAllow;
    }

    public void setEntryAllow(Boolean entryAllow) {
        this.entryAllow = entryAllow;
    }

    public Boolean getImageAllow() {
        return imageAllow;
    }

    public void setImageAllow(Boolean imageAllow) {
        this.imageAllow = imageAllow;
    }

    public Boolean getGPSMandatory() {
        return gPSMandatory;
    }

    public void setGPSMandatory(Boolean gPSMandatory) {
        this.gPSMandatory = gPSMandatory;
    }

}
