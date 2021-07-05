
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImplementationQuestionGetterSetter {

    @SerializedName("Implementation_Question")
    @Expose
    private List<ImplementationQuestion> implementationQuestion = null;

    public List<ImplementationQuestion> getImplementationQuestion() {
        return implementationQuestion;
    }

    public void setImplementationQuestion(List<ImplementationQuestion> implementationQuestion) {
        this.implementationQuestion = implementationQuestion;
    }

}
