
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterFeedbackAnswer {

    @SerializedName("AnswerId")
    @Expose
    private Integer answerId;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("QuestionId")
    @Expose
    private Integer questionId;
    @SerializedName("RightAnswer")
    @Expose
    private Boolean rightAnswer;
    @SerializedName("ImageAllow")
    @Expose
    private Boolean imageAllow;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Boolean getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Boolean getImageAllow() {
        return imageAllow;
    }

    public void setImageAllow(Boolean imageAllow) {
        this.imageAllow = imageAllow;
    }

}
