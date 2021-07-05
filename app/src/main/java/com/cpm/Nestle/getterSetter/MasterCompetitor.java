package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterCompetitor {

    @SerializedName("CompanyId")
    @Expose
    private Integer companyId;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("SubCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("SubCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("BrandName")
    @Expose
    private String brandName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

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

    String Ischecked="";
    String displayType="";
    String remark="";

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVisibility_img() {
        return visibility_img;
    }

    public void setVisibility_img(String visibility_img) {
        this.visibility_img = visibility_img;
    }

    String visibility_img="";

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    String keyId;
    int displayTypeId=0;

    public int getDisplayTypeId() {
        return displayTypeId;
    }

    public void setDisplayTypeId(int displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public String getIschecked() {
        return Ischecked;
    }

    public void setIschecked(String ischecked) {
        Ischecked = ischecked;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
