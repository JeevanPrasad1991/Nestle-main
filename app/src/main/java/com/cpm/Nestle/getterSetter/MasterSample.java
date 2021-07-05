package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterSample {

    @SerializedName("SampleId")
    @Expose
    private Integer sampleId;
    @SerializedName("Sample")
    @Expose
    private String sample;

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    String keyId, mobileNo = "", feedBack = "", name = "", remark = "";

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    String  isChecked="";

    String issueCategory="",issueType="";
    int issueCategoryId =0;

    public String getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public int getIssueCategoryId() {
        return issueCategoryId;
    }

    public void setIssueCategoryId(int issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
    }

    public int getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(int issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    int issueTypeId = 0;

}
