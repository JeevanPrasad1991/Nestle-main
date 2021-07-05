
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterPOSMTypeGetterSetter {

    @SerializedName("Master_PosmType")
    @Expose
    private List<MasterPosmType> masterPosmType = null;

    public List<MasterPosmType> getMasterPosmType() {
        return masterPosmType;
    }

    public void setMasterPosmType(List<MasterPosmType> masterPosmType) {
        this.masterPosmType = masterPosmType;
    }

}
