
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoverageReportGetterSetter {

    @SerializedName("Coverage_Report")
    @Expose
    private List<CoverageReport> coverageReport = null;

    public List<CoverageReport> getCoverageReport() {
        return coverageReport;
    }

    public void setCoverageReport(List<CoverageReport> coverageReport) {
        this.coverageReport = coverageReport;
    }

}
