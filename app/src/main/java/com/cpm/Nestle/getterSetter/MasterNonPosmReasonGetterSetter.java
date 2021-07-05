
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterNonPosmReasonGetterSetter {
    @SerializedName("Master_NonPosmReason")
    @Expose
    private List<MasterNonPosmReason> masterNonPosmReason = null;
    public List<MasterNonPosmReason> getMasterNonPosmReason() {
        return masterNonPosmReason;
    }

}
