
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackMasterGetterSetter {

    @SerializedName("Feedback_Master")
    @Expose
    private List<FeedbackMaster> feedbackMaster = null;

    public List<FeedbackMaster> getFeedbackMaster() {
        return feedbackMaster;
    }

    public void setFeedbackMaster(List<FeedbackMaster> feedbackMaster) {
        this.feedbackMaster = feedbackMaster;
    }

}
