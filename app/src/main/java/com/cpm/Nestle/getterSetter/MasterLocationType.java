
package com.cpm.Nestle.getterSetter;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterLocationType implements Serializable
{

    @SerializedName("LocationTypeId")
    @Expose
    private Integer locationTypeId;
    @SerializedName("LocationType")
    @Expose
    private String locationType;
    private final static long serialVersionUID = 3566567299727615954L;

    public Integer getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(Integer locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

}
