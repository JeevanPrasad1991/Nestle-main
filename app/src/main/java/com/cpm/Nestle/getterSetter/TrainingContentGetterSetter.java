
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainingContentGetterSetter {

    @SerializedName("Training_Content")
    @Expose
    private List<TrainingContent> trainingContent = null;

    public List<TrainingContent> getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingContent(List<TrainingContent> trainingContent) {
        this.trainingContent = trainingContent;
    }

}
