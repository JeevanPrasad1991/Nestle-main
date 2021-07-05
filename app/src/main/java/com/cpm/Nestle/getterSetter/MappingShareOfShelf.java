
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingShareOfShelf {
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("ChainId")
    @Expose
    private Integer chainId;
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;
    @SerializedName("SubCategoryId")
    @Expose
    private Integer subCategoryId;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public MappingShareOfShelf withStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public MappingShareOfShelf withChainId(Integer chainId) {
        this.chainId = chainId;
        return this;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public MappingShareOfShelf withStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
        return this;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public MappingShareOfShelf withSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
        return this;
    }
}
