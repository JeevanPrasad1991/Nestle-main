package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterIssueCategory {

    @SerializedName("IssueCategoryId")
    @Expose
    private Integer issueCategoryId;
    @SerializedName("IssueCategory")
    @Expose
    private String issueCategory;

    public Integer getIssueCategoryId() {
        return issueCategoryId;
    }

    public void setIssueCategoryId(Integer issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
    }

    public MasterIssueCategory withIssueCategoryId(Integer issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
        return this;
    }

    public String getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
    }

    public MasterIssueCategory withIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
        return this;
    }

}