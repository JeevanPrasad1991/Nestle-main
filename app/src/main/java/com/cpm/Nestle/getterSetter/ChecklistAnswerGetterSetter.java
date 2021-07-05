
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistAnswerGetterSetter {

    @SerializedName("Checklist_Answer")
    @Expose
    private List<ChecklistAnswer> checklistAnswer = null;

    public List<ChecklistAnswer> getChecklistAnswer() {
        return checklistAnswer;
    }

    public void setChecklistAnswer(List<ChecklistAnswer> checklistAnswer) {
        this.checklistAnswer = checklistAnswer;
    }

}
