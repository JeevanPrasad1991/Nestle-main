package com.cpm.Nestle.getterSetter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingVisicoolerChecklist {
    @SerializedName("VCCategoryId")
    @Expose
    private Integer vCCategoryId;
    @SerializedName("ChecklistId")
    @Expose
    private Integer checklistId;

    public Integer getVCCategoryId() {
        return vCCategoryId;
    }

    public void setVCCategoryId(Integer vCCategoryId) {
        this.vCCategoryId = vCCategoryId;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

}
