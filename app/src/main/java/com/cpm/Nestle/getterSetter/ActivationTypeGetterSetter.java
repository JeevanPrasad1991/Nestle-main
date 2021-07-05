
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivationTypeGetterSetter {

    @SerializedName("Master_ActivationType")
    @Expose
    private List<MasterActivationType> masterActivationType = null;

    public List<MasterActivationType> getMasterActivationType() {
        return masterActivationType;
    }

    public void setMasterActivationType(List<MasterActivationType> masterActivationType) {
        this.masterActivationType = masterActivationType;
    }

}
