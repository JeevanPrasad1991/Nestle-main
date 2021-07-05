
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterChecklistAnswer {

    @SerializedName("AnswerId")
    @Expose
    private Integer answerId;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("ChecklistId")
    @Expose
    private Integer checklistId;
    @SerializedName("Rating")
    @Expose
    private Integer rating;
    @SerializedName("ImageAllow1")
    @Expose
    private Boolean imageAllow1;
    @SerializedName("ImageAllow2")
    @Expose
    private Boolean imageAllow2;
    @SerializedName("ImageLable1")
    @Expose
    private String imageLable1;

    @SerializedName("ImageLable2")
    @Expose
    private String imageLable2;

    @SerializedName("ImageGridAllow1")
    @Expose
    private boolean imageGridAllow1;

    public String getImageLable1() {
        return imageLable1;
    }

    public void setImageLable1(String imageLable1) {
        this.imageLable1 = imageLable1;
    }

    public String getImageLable2() {
        return imageLable2;
    }

    public void setImageLable2(String imageLable2) {
        this.imageLable2 = imageLable2;
    }

    public boolean getImageGridAllow1() {
        return imageGridAllow1;
    }

    public void setImageGridAllow1(boolean imageGridAllow1) {
        this.imageGridAllow1 = imageGridAllow1;
    }

    public boolean getImageGridAllow2() {
        return imageGridAllow2;
    }

    public void setImageGridAllow2(boolean imageGridAllow2) {
        this.imageGridAllow2 = imageGridAllow2;
    }

    @SerializedName("ImageGridAllow2")
    @Expose
    private boolean imageGridAllow2;


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

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getImageAllow1() {
        return imageAllow1;
    }

    public void setImageAllow1(Boolean imageAllow1) {
        this.imageAllow1 = imageAllow1;
    }

    public Boolean getImageAllow2() {
        return imageAllow2;
    }

    public void setImageAllow2(Boolean imageAllow2) {
        this.imageAllow2 = imageAllow2;
    }

}
