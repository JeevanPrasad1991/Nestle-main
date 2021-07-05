
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterFeedbackQuestionGetterSetter {

    @SerializedName("Master_FeedbackQuestion")
    @Expose
    private List<MasterFeedbackQuestion> masterFeedbackQuestion = null;

    public List<MasterFeedbackQuestion> getMasterFeedbackQuestion() {
        return masterFeedbackQuestion;
    }

    public void setMasterFeedbackQuestion(List<MasterFeedbackQuestion> masterFeedbackQuestion) {
        this.masterFeedbackQuestion = masterFeedbackQuestion;
    }

}
