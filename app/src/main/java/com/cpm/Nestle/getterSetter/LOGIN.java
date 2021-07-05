
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LOGIN {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("AppVersion")
    @Expose
    private Integer appVersion;
    @SerializedName("AppPath")
    @Expose
    private String appPath;
    @SerializedName("Result")
    @Expose
    private Integer result;
    @SerializedName("VisitDate")
    @Expose
    private String visitDate;
    @SerializedName("PasswordStatus")
    @Expose
    private String passwordStatus;
    @SerializedName("SecurityToken")
    @Expose
    private String securityToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Integer appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(String passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

}
