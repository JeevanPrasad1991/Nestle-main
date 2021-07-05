
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterFeedbackQuestion {

    @SerializedName("QuestionCategoryId")
    @Expose
    private Integer questionCategoryId;
    @SerializedName("QuestionCategory")
    @Expose
    private String questionCategory;
    @SerializedName("QuestionId")
    @Expose
    private Integer questionId;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("QuestionType")
    @Expose
    private String questionType;
    @SerializedName("QuestionSequence")
    @Expose
    private Integer questionSequence;

    public Integer getQuestionCategoryId() {
        return questionCategoryId;
    }

    public void setQuestionCategoryId(Integer questionCategoryId) {
        this.questionCategoryId = questionCategoryId;
    }

    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(Integer questionSequence) {
        this.questionSequence = questionSequence;
    }

}
