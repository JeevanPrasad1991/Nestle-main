package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingVisibility {

    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("SubCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("DisplayId")
    @Expose
    private Integer displayId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public MappingVisibility withStoreId(Integer storeId) {
        this.storeId = storeId;
        return this;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public MappingVisibility withSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
        return this;
    }

    public Integer getDisplayId() {
        return displayId;
    }

    public void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }

    public MappingVisibility withDisplayId(Integer displayId) {
        this.displayId = displayId;
        return this;
    }

}