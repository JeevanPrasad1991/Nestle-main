
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetMaster {

    @SerializedName("Asset_Id")
    @Expose
    private Integer assetId;
    @SerializedName("Asset_Name")
    @Expose
    private String assetName;
    @SerializedName("RefImages")
    @Expose
    private String refImages;

    public String getRefImages() {
        return refImages;
    }

    public void setRefImages(String refImages) {
        this.refImages = refImages;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    String brand_Id;
    String brand;
    String initiativeIMG = "";
    String checklist_Id;
    String checklist;
    String image;
    String Window_Image_refrance;
    String image2;
    boolean exist;
    boolean isFilled;
    String checklistRightAns_Id = "";
    String ckecklist_anserId;
    String checklist_answer;
    String initiativeRightAns = "";
    String initiativeRightAns_Id = "";
    int reasonId;
    String reason;
    String temp;
    String img = "";

    public String getKey_Id() {
        return key_Id;
    }

    public void setKey_Id(String key_Id) {
        this.key_Id = key_Id;
    }

    String key_Id;



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    String answered;
    int answered_id = -1;
    int nonExecutionReasonId = 0;

    public String getBrand_Id() {
        return brand_Id;
    }

    public void setBrand_Id(String brand_Id) {
        this.brand_Id = brand_Id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getInitiativeIMG() {
        return initiativeIMG;
    }

    public void setInitiativeIMG(String initiativeIMG) {
        this.initiativeIMG = initiativeIMG;
    }

    public String getChecklist_Id() {
        return checklist_Id;
    }

    public void setChecklist_Id(String checklist_Id) {
        this.checklist_Id = checklist_Id;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWindow_Image_refrance() {
        return Window_Image_refrance;
    }

    public void setWindow_Image_refrance(String window_Image_refrance) {
        Window_Image_refrance = window_Image_refrance;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public String getChecklistRightAns_Id() {
        return checklistRightAns_Id;
    }

    public void setChecklistRightAns_Id(String checklistRightAns_Id) {
        this.checklistRightAns_Id = checklistRightAns_Id;
    }

    public String getCkecklist_anserId() {
        return ckecklist_anserId;
    }

    public void setCkecklist_anserId(String ckecklist_anserId) {
        this.ckecklist_anserId = ckecklist_anserId;
    }

    public String getChecklist_answer() {
        return checklist_answer;
    }

    public void setChecklist_answer(String checklist_answer) {
        this.checklist_answer = checklist_answer;
    }

    public String getInitiativeRightAns() {
        return initiativeRightAns;
    }

    public void setInitiativeRightAns(String initiativeRightAns) {
        this.initiativeRightAns = initiativeRightAns;
    }

    public String getInitiativeRightAns_Id() {
        return initiativeRightAns_Id;
    }

    public void setInitiativeRightAns_Id(String initiativeRightAns_Id) {
        this.initiativeRightAns_Id = initiativeRightAns_Id;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public int getAnswered_id() {
        return answered_id;
    }

    public void setAnswered_id(int answered_id) {
        this.answered_id = answered_id;
    }

    public int getNonExecutionReasonId() {
        return nonExecutionReasonId;
    }

    public void setNonExecutionReasonId(int nonExecutionReasonId) {
        this.nonExecutionReasonId = nonExecutionReasonId;
    }
}
