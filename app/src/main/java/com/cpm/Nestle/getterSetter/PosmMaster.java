
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PosmMaster {

    @SerializedName("Posm_Id")
    @Expose
    private Integer posmId;
    @SerializedName("Posm")
    @Expose
    private String posm;
    @SerializedName("Posm_Type_Id")
    @Expose
    private Integer posmTypeId;
    @SerializedName("Posm_Type")
    @Expose
    private String posmType;

    public Integer getPosmId() {
        return posmId;
    }

    public void setPosmId(Integer posmId) {
        this.posmId = posmId;
    }

    public String getPosm() {
        return posm;
    }

    public void setPosm(String posm) {
        this.posm = posm;
    }

    public Integer getPosmTypeId() {
        return posmTypeId;
    }

    public void setPosmTypeId(Integer posmTypeId) {
        this.posmTypeId = posmTypeId;
    }

    public String getPosmType() {
        return posmType;
    }

    public void setPosmType(String posmType) {
        this.posmType = posmType;
    }
    String image="";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String CurrectanswerCd,Currectanswer;
    private String Answer;
    private Integer answerId;

    public String getCurrectanswerCd() {
        return CurrectanswerCd;
    }

    public void setCurrectanswerCd(String currectanswerCd) {
        CurrectanswerCd = currectanswerCd;
    }

    public String getCurrectanswer() {
        return Currectanswer;
    }

    public void setCurrectanswer(String currectanswer) {
        Currectanswer = currectanswer;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    String brandId="";

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    String brand="";

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
    int isChecked=-1;
    int keyId;

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
    int reasonId;
    String reason;

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
    String status="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
