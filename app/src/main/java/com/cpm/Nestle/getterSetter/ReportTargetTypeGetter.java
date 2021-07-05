
package com.cpm.Nestle.getterSetter;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportTargetTypeGetter implements Serializable
{

    @SerializedName("ReportCoverage_MTD")
    @Expose
    private List<ReportCoverageMTD> reportCoverageMTD = null;
    private final static long serialVersionUID = 1589333334493990727L;

    public List<ReportCoverageMTD> getReportCoverageMTD() {
        return reportCoverageMTD;
    }

    public void setReportCoverageMTD(List<ReportCoverageMTD> reportCoverageMTD) {
        this.reportCoverageMTD = reportCoverageMTD;
    }

}
