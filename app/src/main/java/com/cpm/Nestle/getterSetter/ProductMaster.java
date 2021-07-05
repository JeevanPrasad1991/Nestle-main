
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductMaster {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductSequence")
    @Expose
    private Integer productSequence;
    @SerializedName("Mrp")
    @Expose
    private Double mrp;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductSequence() {
        return productSequence;
    }

    public void setProductSequence(Integer productSequence) {
        this.productSequence = productSequence;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }
}
