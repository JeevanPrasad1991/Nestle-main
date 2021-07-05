package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PerformancePageGetterSetter {

    @SerializedName("Performance")
    @Expose
    private List<PerformancePage> performancePage = null;

    public List<PerformancePage> getPerformancePage() {
        return performancePage;
    }

    public void setPerformancePage(List<PerformancePage> performancePage) {
        this.performancePage = performancePage;
    }
}
