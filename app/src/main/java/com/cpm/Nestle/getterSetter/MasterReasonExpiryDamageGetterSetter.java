
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterReasonExpiryDamageGetterSetter {

    @SerializedName("Master_ReasonExpiryDamage")
    @Expose
    private List<MasterReasonExpiryDamage> masterReasonExpiryDamage = null;

    public List<MasterReasonExpiryDamage> getMasterReasonExpiryDamage() {
        return masterReasonExpiryDamage;
    }

    public void setMasterReasonExpiryDamage(List<MasterReasonExpiryDamage> masterReasonExpiryDamage) {
        this.masterReasonExpiryDamage = masterReasonExpiryDamage;
    }

}
