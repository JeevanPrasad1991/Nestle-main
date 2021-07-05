
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviousOrderGiven {

    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("ProductId")
    @Expose
    private Integer productId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("OrderQty")
    @Expose
    private Integer orderQty;
    @SerializedName("Amount")
    @Expose
    private Double amount;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
