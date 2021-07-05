
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterProductFlavour {

    @SerializedName("ProductFlavour")
    @Expose
    private String productFlavour;

    public String getProductFlavour() {
        return productFlavour;
    }

    public void setProductFlavour(String productFlavour) {
        this.productFlavour = productFlavour;
    }

}
