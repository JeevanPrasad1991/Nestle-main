
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingCTU {

    @SerializedName("Store_Id")
    @Expose
    private Integer storeId;
    @SerializedName("Brand_Id")
    @Expose
    private Integer brandId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

}
