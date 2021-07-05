
package com.cpm.Nestle.getterSetter;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportCoverageMTD implements Serializable
{

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Planned_Outlets")
    @Expose
    private Integer plannedOutlets;
    @SerializedName("Covered_Outlets")
    @Expose
    private Integer coveredOutlets;
    @SerializedName("Coverage_Per")
    @Expose
    private Float coveragePer;
    @SerializedName("Pob")
    @Expose
    private Float pob;
    @SerializedName("Target")
    @Expose
    private Integer target;
    @SerializedName("PerValue")
    @Expose
    private Float perValue;
    @SerializedName("OrderValue")
    @Expose
    private Float orderValue;
    private final static long serialVersionUID = -6712890925736400946L;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPlannedOutlets() {
        return plannedOutlets;
    }

    public void setPlannedOutlets(Integer plannedOutlets) {
        this.plannedOutlets = plannedOutlets;
    }

    public Integer getCoveredOutlets() {
        return coveredOutlets;
    }

    public void setCoveredOutlets(Integer coveredOutlets) {
        this.coveredOutlets = coveredOutlets;
    }

    public Float getCoveragePer() {
        return coveragePer;
    }

    public void setCoveragePer(Float coveragePer) {
        this.coveragePer = coveragePer;
    }

    public Float getPob() {
        return pob;
    }

    public void setPob(Float pob) {
        this.pob = pob;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Float getPerValue() {
        return perValue;
    }

    public void setPerValue(Float perValue) {
        this.perValue = perValue;
    }

    public Float getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Float orderValue) {
        this.orderValue = orderValue;
    }

}
