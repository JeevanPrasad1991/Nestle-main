
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterProductTypeGetterSetter {

    @SerializedName("Master_ProductType")
    @Expose
    private List<MasterProductType> masterProductType = null;

    public List<MasterProductType> getMasterProductType() {
        return masterProductType;
    }

    public void setMasterProductType(List<MasterProductType> masterProductType) {
        this.masterProductType = masterProductType;
    }

}
