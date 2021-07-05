
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterPromoType {
    @SerializedName("PromoTypeId")
    @Expose
    private Integer promoTypeId;
    @SerializedName("PromoType")
    @Expose
    private String promoType;

    public Integer getPromoTypeId() {
        return promoTypeId;
    }

    public void setPromoTypeId(Integer promoTypeId) {
        this.promoTypeId = promoTypeId;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }
}
