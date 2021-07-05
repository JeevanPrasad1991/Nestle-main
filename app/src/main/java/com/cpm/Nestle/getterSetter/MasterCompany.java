package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterCompany {

    @SerializedName("CompanyId")
    @Expose
    private Integer companyId;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("IsCompetitor")
    @Expose
    private Boolean isCompetitor;
    @SerializedName("CompanySequence")
    @Expose
    private Integer companySequence;

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

    public Boolean getIsCompetitor() {
        return isCompetitor;
    }

    public void setIsCompetitor(Boolean isCompetitor) {
        this.isCompetitor = isCompetitor;
    }

    public Integer getCompanySequence() {
        return companySequence;
    }

    public void setCompanySequence(Integer companySequence) {
        this.companySequence = companySequence;
    }

    String promoType = "";
    String desc = "";
    String comp_img1 = "";
    String comp_img2 = "";
    String Ispresent = "",categorY="";
    int categoryId=0;

    public String getCategorY() {
        return categorY;
    }

    public void setCategorY(String categorY) {
        this.categorY = categorY;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getCompetitor() {
        return isCompetitor;
    }

    public void setCompetitor(Boolean competitor) {
        isCompetitor = competitor;
    }
    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getComp_img1() {
        return comp_img1;
    }

    public void setComp_img1(String comp_img1) {
        this.comp_img1 = comp_img1;
    }

    public String getComp_img2() {
        return comp_img2;
    }

    public void setComp_img2(String comp_img2) {
        this.comp_img2 = comp_img2;
    }

    public String getIspresent() {
        return Ispresent;
    }

    public void setIspresent(String ispresent) {
        Ispresent = ispresent;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getProTypeId() {
        return proTypeId;
    }

    public void setProTypeId(int proTypeId) {
        this.proTypeId = proTypeId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    String brand_name = "";

    public String getKeyId() {
        return KeyId;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    String KeyId,asset_name="";
    int proTypeId = 0;
    int brandId = 0;

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    int assetId=0;

}
