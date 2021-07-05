
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImplementationQuestion {

    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("QuestionId")
    @Expose
    private Integer questionId;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("AnswerId")
    @Expose
    private Integer answerId;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("ImageAllow")
    @Expose
    private Boolean imageAllow;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Boolean getImageAllow() {
        return imageAllow;
    }

    public void setImageAllow(Boolean imageAllow) {
        this.imageAllow = imageAllow;
    }

}
