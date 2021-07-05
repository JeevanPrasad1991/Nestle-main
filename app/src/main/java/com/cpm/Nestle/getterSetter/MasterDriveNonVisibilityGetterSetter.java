
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterDriveNonVisibilityGetterSetter {

    @SerializedName("Master_DriveNonVisibility")
    @Expose
    private List<MasterDriveNonVisibility> masterDriveNonVisibility = null;

    public List<MasterDriveNonVisibility> getMasterDriveNonVisibility() {
        return masterDriveNonVisibility;
    }

    public void setMasterDriveNonVisibility(List<MasterDriveNonVisibility> masterDriveNonVisibility) {
        this.masterDriveNonVisibility = masterDriveNonVisibility;
    }

}
