
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterWindowChecklist {

    @SerializedName("WindowId")
    @Expose
    private Integer windowId;
    @SerializedName("ChecklistId")
    @Expose
    private Integer checklistId;
    @SerializedName("Checklist")
    @Expose
    private String checklist;

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

}
