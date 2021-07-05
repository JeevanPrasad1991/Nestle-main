
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterBrand {

    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("CompanyId")
    @Expose
    private Integer companyId;
    @SerializedName("BrandSequence")
    @Expose
    private Integer brandSequence;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    public Integer getBrandSequence() {
        return brandSequence;
    }

    public void setBrandSequence(Integer brandSequence) {
        this.brandSequence = brandSequence;
    }



}
