
package com.cpm.Nestle.getterSetter;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayMasterGetterSetter implements Serializable {

    @SerializedName("Display_Master")
    @Expose
    private List<DisplayMaster> displayMaster = null;

    public List<DisplayMaster> getDisplayMaster() {
        return displayMaster;
    }

    public void setDisplayMaster(List<DisplayMaster> displayMaster) {
        this.displayMaster = displayMaster;
    }

}
