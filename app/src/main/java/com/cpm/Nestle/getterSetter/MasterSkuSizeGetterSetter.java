
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterSkuSizeGetterSetter {

    @SerializedName("Master_SkuSize")
    @Expose
    private List<MasterSkuSize> masterSkuSize = null;

    public List<MasterSkuSize> getMasterSkuSize() {
        return masterSkuSize;
    }

    public void setMasterSkuSize(List<MasterSkuSize> masterSkuSize) {
        this.masterSkuSize = masterSkuSize;
    }

}
