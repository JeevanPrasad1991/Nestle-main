package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterDocument {

    @SerializedName("DocumentId")
    @Expose
    private Integer documentId;
    @SerializedName("DocumentName")
    @Expose
    private String documentName;
    @SerializedName("Image1")
    @Expose
    private Boolean image1;
    @SerializedName("Image2")
    @Expose
    private Boolean image2;

    public String getUpload_status() {
        return upload_status;
    }

    public void setUpload_status(String upload_status) {
        this.upload_status = upload_status;
    }

    String upload_status="N";

    public String getStr_image_first() {
        return str_image_first;
    }

    public void setStr_image_first(String str_image_first) {
        this.str_image_first = str_image_first;
    }

    public String getStr_image_second() {
        return str_image_second;
    }

    public void setStr_image_second(String str_image_second) {
        this.str_image_second = str_image_second;
    }

    public String getAnswer_name() {
        return answer_name;
    }

    public void setAnswer_name(String answer_name) {
        this.answer_name = answer_name;
    }

    @SerializedName("AnswerList")
    @Expose
    private String answerList;

    String str_image_first="",str_image_second="",answer_name="";
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public MasterDocument withDocumentId(Integer documentId) {
        this.documentId = documentId;
        return this;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public MasterDocument withDocumentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public Boolean getImage1() {
        return image1;
    }

    public void setImage1(Boolean image1) {
        this.image1 = image1;
    }

    public MasterDocument withImage1(Boolean image1) {
        this.image1 = image1;
        return this;
    }

    public Boolean getImage2() {
        return image2;
    }

    public void setImage2(Boolean image2) {
        this.image2 = image2;
    }

    public MasterDocument withImage2(Boolean image2) {
        this.image2 = image2;
        return this;
    }

    public String getAnswerList() {
        return answerList;
    }

    public void setAnswerList(String answerList) {
        this.answerList = answerList;
    }

    public MasterDocument withAnswerList(String answerList) {
        this.answerList = answerList;
        return this;
    }

}
