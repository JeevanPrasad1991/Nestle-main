package com.cpm.Nestle.getterSetter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterAssetLocation {

    @SerializedName("LocationId")
    @Expose
    private Integer locationId;
    @SerializedName("LocationName")
    @Expose
    private String locationName;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
