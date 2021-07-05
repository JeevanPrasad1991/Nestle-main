
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistMasterGetterSetter {

    @SerializedName("Checklist_Master")
    @Expose
    private List<ChecklistMaster> checklistMaster = null;

    public List<ChecklistMaster> getChecklistMaster() {
        return checklistMaster;
    }

    public void setChecklistMaster(List<ChecklistMaster> checklistMaster) {
        this.checklistMaster = checklistMaster;
    }

}
