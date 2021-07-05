
package com.cpm.Nestle.getterSetter;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingPromotionGetterSetter {
    @SerializedName("Master_NonPromotionReason")
    @Expose
    private List<MasterNonPromotionReason> masterNonPromotionReason = null;

    public List<MasterNonPromotionReason> getMasterNonPromotionReason() {
        return masterNonPromotionReason;
    }

    @SerializedName("Mapping_Promotion")
    @Expose
    private List<MappingPromotion> mappingPromotion = null;

    public List<MappingPromotion> getMappingPromotion() {
        return mappingPromotion;
    }
    @SerializedName("Master_PromoType")
    @Expose
    private List<MasterPromoType> masterPromoType = null;

    public List<MasterPromoType> getMasterPromoType() {
        return masterPromoType;
    }

}
