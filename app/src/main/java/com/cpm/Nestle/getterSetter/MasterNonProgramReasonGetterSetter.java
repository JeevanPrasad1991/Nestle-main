
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MasterNonProgramReasonGetterSetter {

    @SerializedName("Master_NonProgramReason")
    @Expose
    private List<MasterNonProgramReason> masterNonProgramReason = null;

    public List<MasterNonProgramReason> getMasterNonProgramReason() {
        return masterNonProgramReason;
    }

    public void setMasterNonProgramReason(List<MasterNonProgramReason> masterNonProgramReason) {
        this.masterNonProgramReason = masterNonProgramReason;
    }

}
