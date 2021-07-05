
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingVisicooler {
    @SerializedName("VCId")
    @Expose
    private Integer vCId;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("VCCategoryId")
    @Expose
    private Integer vCCategoryId;
    @SerializedName("VisicoolerCategory")
    @Expose
    private String visicoolerCategory;
    @SerializedName("Make")
    @Expose
    private String make;
    @SerializedName("VCType")
    @Expose
    private String vCType;

    public Integer getVCId() {
        return vCId;
    }

    public void setVCId(Integer vCId) {
        this.vCId = vCId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getVCCategoryId() {
        return vCCategoryId;
    }

    public void setVCCategoryId(Integer vCCategoryId) {
        this.vCCategoryId = vCCategoryId;
    }

    public String getVisicoolerCategory() {
        return visicoolerCategory;
    }

    public void setVisicoolerCategory(String visicoolerCategory) {
        this.visicoolerCategory = visicoolerCategory;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getVCType() {
        return vCType;
    }

    public void setVCType(String vCType) {
        this.vCType = vCType;
    }

}
