
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterPosmGetterSetter {

    @SerializedName("Master_Posm")
    @Expose
    private List<MasterPosm> masterPosm = null;

    public List<MasterPosm> getMasterPosm() {
        return masterPosm;
    }
}
