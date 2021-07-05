package com.cpm.Nestle.getterSetter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingSubProgramChecklist {
    @SerializedName("SubProgramId")
    @Expose
    private Integer subProgramId;
    @SerializedName("ChecklistId")
    @Expose
    private Integer checklistId;

    public Integer getSubProgramId() {
        return subProgramId;
    }

    public void setSubProgramId(Integer subProgramId) {
        this.subProgramId = subProgramId;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

}