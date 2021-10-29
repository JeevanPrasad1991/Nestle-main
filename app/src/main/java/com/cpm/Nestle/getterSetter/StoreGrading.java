package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreGrading {
    @SerializedName("Srno")
    @Expose
    private Integer srno;
    @SerializedName("Period")
    @Expose
    private String period;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("ProgramName")
    @Expose
    private String programName;
    @SerializedName("Grade")
    @Expose
    private String grade;

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
