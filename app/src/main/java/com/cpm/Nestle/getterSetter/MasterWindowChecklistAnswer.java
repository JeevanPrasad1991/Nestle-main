
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterWindowChecklistAnswer {

    @SerializedName("ChecklistAnswerId")
    @Expose
    private Integer checklistAnswerId;
    @SerializedName("ChecklistAnswer")
    @Expose
    private String checklistAnswer;
    @SerializedName("ChecklistId")
    @Expose
    private Integer checklistId;

    public Integer getChecklistAnswerId() {
        return checklistAnswerId;
    }

    public void setChecklistAnswerId(Integer checklistAnswerId) {
        this.checklistAnswerId = checklistAnswerId;
    }

    public String getChecklistAnswer() {
        return checklistAnswer;
    }

    public void setChecklistAnswer(String checklistAnswer) {
        this.checklistAnswer = checklistAnswer;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

}
