
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterDisplayType {

    @SerializedName("DisplayTypeId")
    @Expose
    private Integer displayTypeId;
    @SerializedName("DisplayType")
    @Expose
    private String displayType;

    public Integer getDisplayTypeId() {
        return displayTypeId;
    }

    public void setDisplayTypeId(Integer displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

}
