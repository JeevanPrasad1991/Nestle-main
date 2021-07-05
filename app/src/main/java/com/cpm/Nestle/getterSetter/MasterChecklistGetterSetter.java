
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterChecklistGetterSetter {

    @SerializedName("Master_Checklist")
    @Expose
    private List<MasterChecklist> masterChecklist = null;

    public List<MasterChecklist> getMasterChecklist() {
        return masterChecklist;
    }

}
