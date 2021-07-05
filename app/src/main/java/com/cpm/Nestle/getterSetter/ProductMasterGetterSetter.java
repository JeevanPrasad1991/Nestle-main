
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductMasterGetterSetter {

    @SerializedName("Master_Product")
    @Expose
    private List<ProductMaster> productMaster = null;

    public List<ProductMaster> getProductMaster() {
        return productMaster;
    }


    @SerializedName("Master_Sample")
    @Expose
    private List<MasterSample> masterSample = null;

    public List<MasterSample> getMasterSample() {
        return masterSample;
    }
}
