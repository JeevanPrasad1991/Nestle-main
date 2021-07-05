package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterIssueType {

    @SerializedName("IssueTypeId")
    @Expose
    private Integer issueTypeId;
    @SerializedName("IssueType")
    @Expose
    private String issueType;
    @SerializedName("IssueCategoryId")
    @Expose
    private Integer issueCategoryId;

    public Integer getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Integer issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public MasterIssueType withIssueTypeId(Integer issueTypeId) {
        this.issueTypeId = issueTypeId;
        return this;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public MasterIssueType withIssueType(String issueType) {
        this.issueType = issueType;
        return this;
    }

    public Integer getIssueCategoryId() {
        return issueCategoryId;
    }

    public void setIssueCategoryId(Integer issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
    }

    public MasterIssueType withIssueCategoryId(Integer issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
        return this;
    }

}