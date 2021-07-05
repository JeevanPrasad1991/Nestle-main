
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterWindowChecklistAnswerGetterSetter {

    @SerializedName("Master_WindowChecklistAnswer")
    @Expose
    private List<MasterWindowChecklistAnswer> masterWindowChecklistAnswer = null;

    public List<MasterWindowChecklistAnswer> getMasterWindowChecklistAnswer() {
        return masterWindowChecklistAnswer;
    }

    public void setMasterWindowChecklistAnswer(List<MasterWindowChecklistAnswer> masterWindowChecklistAnswer) {
        this.masterWindowChecklistAnswer = masterWindowChecklistAnswer;
    }

}
