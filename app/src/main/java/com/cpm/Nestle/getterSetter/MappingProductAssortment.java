
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingProductAssortment {

    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;
    @SerializedName("StoreCategoryId")
    @Expose
    private Integer storeCategoryId;
    @SerializedName("StoreClassId")
    @Expose
    private Integer storeClassId;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("MSL")
    @Expose
    private Boolean mSL;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public Integer getStoreCategoryId() {
        return storeCategoryId;
    }

    public void setStoreCategoryId(Integer storeCategoryId) {
        this.storeCategoryId = storeCategoryId;
    }

    public Integer getStoreClassId() {
        return storeClassId;
    }

    public void setStoreClassId(Integer storeClassId) {
        this.storeClassId = storeClassId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Boolean getMSL() {
        return mSL;
    }

    public void setMSL(Boolean mSL) {
        this.mSL = mSL;
    }

}
