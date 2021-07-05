
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingRDVisibilityDriveGetterSetter {

    @SerializedName("Mapping_RD_VisibilityDrive")
    @Expose
    private List<MappingRDVisibilityDrive> mappingRDVisibilityDrive = null;

    public List<MappingRDVisibilityDrive> getMappingRDVisibilityDrive() {
        return mappingRDVisibilityDrive;
    }

    public void setMappingRDVisibilityDrive(List<MappingRDVisibilityDrive> mappingRDVisibilityDrive) {
        this.mappingRDVisibilityDrive = mappingRDVisibilityDrive;
    }

}
