
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterWindowChecklistGetterSetter {

    @SerializedName("Master_WindowChecklist")
    @Expose
    private List<MasterWindowChecklist> masterWindowChecklist = null;

    public List<MasterWindowChecklist> getMasterWindowChecklist() {
        return masterWindowChecklist;
    }

    public void setMasterWindowChecklist(List<MasterWindowChecklist> masterWindowChecklist) {
        this.masterWindowChecklist = masterWindowChecklist;
    }

}
