
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterSkuStatusGetterSetter {

    @SerializedName("Master_SkuStatus")
    @Expose
    private List<MasterSkustatus> masterSkuStatus = null;

    public List<MasterSkustatus> getMasterSkuStatus() {
        return masterSkuStatus;
    }

    public void setMasterSkuStatus(List<MasterSkustatus> masterSkuStatus) {
        this.masterSkuStatus = masterSkuStatus;
    }

}
