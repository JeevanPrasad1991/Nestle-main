package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformancePage {
    @SerializedName("USER_ID")
    @Expose
    private String uSERID;
    @SerializedName("Performance_Page")
    @Expose
    private String performancePage;

    public String getUSERID() {
        return uSERID;
    }

    public void setUSERID(String uSERID) {
        this.uSERID = uSERID;
    }

    public String getPerformancePage() {
        return performancePage;
    }

    public void setPerformancePage(String performancePage) {
        this.performancePage = performancePage;
    }
}
