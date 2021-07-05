
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterPromoTypeGetterSetter {

    @SerializedName("Master_PromoType")
    @Expose
    private List<MasterPromoType> masterPromoType = null;

    public List<MasterPromoType> getMasterPromoType() {
        return masterPromoType;
    }

    public void setMasterPromoType(List<MasterPromoType> masterPromoType) {
        this.masterPromoType = masterPromoType;
    }

}
