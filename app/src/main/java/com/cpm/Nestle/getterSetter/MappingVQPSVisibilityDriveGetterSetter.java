
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingVQPSVisibilityDriveGetterSetter {

    @SerializedName("Mapping_VQPS_VisibilityDrive")
    @Expose
    private List<MappingVQPSVisibilityDrive> mappingVQPSVisibilityDrive = null;

    public List<MappingVQPSVisibilityDrive> getMappingVQPSVisibilityDrive() {
        return mappingVQPSVisibilityDrive;
    }

    public void setMappingVQPSVisibilityDrive(List<MappingVQPSVisibilityDrive> mappingVQPSVisibilityDrive) {
        this.mappingVQPSVisibilityDrive = mappingVQPSVisibilityDrive;
    }

}
