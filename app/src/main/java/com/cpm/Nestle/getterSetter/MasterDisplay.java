package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterDisplay {

    @SerializedName("DisplayId")
    @Expose
    private Integer displayId;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("RefImage")
    @Expose
    private String refImage;

    public Integer getDisplayId() {
        return displayId;
    }

    public void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }

    public MasterDisplay withDisplayId(Integer displayId) {
        this.displayId = displayId;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public MasterDisplay withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getRefImage() {
        return refImage;
    }

    public void setRefImage(String refImage) {
        this.refImage = refImage;
    }

    public MasterDisplay withRefImage(String refImage) {
        this.refImage = refImage;
        return this;
    }

}