
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterFeedbackAnswerGetterSetter {

    @SerializedName("Master_FeedbackAnswer")
    @Expose
    private List<MasterFeedbackAnswer> masterFeedbackAnswer = null;

    public List<MasterFeedbackAnswer> getMasterFeedbackAnswer() {
        return masterFeedbackAnswer;
    }

    public void setMasterFeedbackAnswer(List<MasterFeedbackAnswer> masterFeedbackAnswer) {
        this.masterFeedbackAnswer = masterFeedbackAnswer;
    }

}
