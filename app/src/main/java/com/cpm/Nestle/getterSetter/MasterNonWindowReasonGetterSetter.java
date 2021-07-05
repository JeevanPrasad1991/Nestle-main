
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterNonWindowReasonGetterSetter {

    @SerializedName("Master_NonWindowReason")
    @Expose
    private List<MasterNonWindowReason> masterNonWindowReason = null;

    public List<MasterNonWindowReason> getMasterNonWindowReason() {
        return masterNonWindowReason;
    }

    public void setMasterNonWindowReason(List<MasterNonWindowReason> masterNonWindowReason) {
        this.masterNonWindowReason = masterNonWindowReason;
    }

}
