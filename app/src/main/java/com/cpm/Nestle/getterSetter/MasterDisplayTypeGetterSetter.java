
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterDisplayTypeGetterSetter {

    @SerializedName("Master_DisplayType")
    @Expose
    private List<MasterDisplayType> masterDisplayType = null;

    public List<MasterDisplayType> getMasterDisplayType() {
        return masterDisplayType;
    }

    public void setMasterDisplayType(List<MasterDisplayType> masterDisplayType) {
        this.masterDisplayType = masterDisplayType;
    }

}
