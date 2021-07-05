
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverageReport {

    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Total_Planned")
    @Expose
    private Integer totalPlanned;
    @SerializedName("Coverage")
    @Expose
    private Integer coverage;
    @SerializedName("Merchandising")
    @Expose
    private Integer merchandising;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalPlanned() {
        return totalPlanned;
    }

    public void setTotalPlanned(Integer totalPlanned) {
        this.totalPlanned = totalPlanned;
    }

    public Integer getCoverage() {
        return coverage;
    }

    public void setCoverage(Integer coverage) {
        this.coverage = coverage;
    }

    public Integer getMerchandising() {
        return merchandising;
    }

    public void setMerchandising(Integer merchandising) {
        this.merchandising = merchandising;
    }

}
