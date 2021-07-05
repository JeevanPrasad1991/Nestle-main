
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchandiserPerformance {

    @SerializedName("Supervisor")
    @Expose
    private String supervisor;
    @SerializedName("Emp_Id")
    @Expose
    private Integer empId;
    @SerializedName("Merchandiser")
    @Expose
    private String merchandiser;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Outlets")
    @Expose
    private Integer outlets;
    @SerializedName("CoveCount")
    @Expose
    private Integer coveCount;
    @SerializedName("Coverage_Per")
    @Expose
    private Double coveragePer;
    @SerializedName("ExecCount")
    @Expose
    private Integer execCount;
    @SerializedName("Exec_Per")
    @Expose
    private Double execPer;
    @SerializedName("Cft")
    @Expose
    private String cft;
    @SerializedName("Fos")
    @Expose
    private String fos;
    @SerializedName("Permission_issue")
    @Expose
    private Integer permissionIssue;
    @SerializedName("Temp_closed")
    @Expose
    private Integer tempClosed;
    @SerializedName("Perm_closed")
    @Expose
    private Integer permClosed;
    @SerializedName("Space_Issue")
    @Expose
    private Integer spaceIssue;
    @SerializedName("Payment_Issue")
    @Expose
    private Integer paymentIssue;
    @SerializedName("Stock_Issue")
    @Expose
    private Integer stockIssue;
    @SerializedName("Kit_rem_by_Ratail")
    @Expose
    private Integer kitRemByRatail;

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getMerchandiser() {
        return merchandiser;
    }

    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getOutlets() {
        return outlets;
    }

    public void setOutlets(Integer outlets) {
        this.outlets = outlets;
    }

    public Integer getCoveCount() {
        return coveCount;
    }

    public void setCoveCount(Integer coveCount) {
        this.coveCount = coveCount;
    }

    public Double getCoveragePer() {
        return coveragePer;
    }

    public void setCoveragePer(Double coveragePer) {
        this.coveragePer = coveragePer;
    }

    public Integer getExecCount() {
        return execCount;
    }

    public void setExecCount(Integer execCount) {
        this.execCount = execCount;
    }

    public Double getExecPer() {
        return execPer;
    }

    public void setExecPer(Double execPer) {
        this.execPer = execPer;
    }

    public String getCft() {
        return cft;
    }

    public void setCft(String cft) {
        this.cft = cft;
    }

    public String getFos() {
        return fos;
    }

    public void setFos(String fos) {
        this.fos = fos;
    }

    public Integer getPermissionIssue() {
        return permissionIssue;
    }

    public void setPermissionIssue(Integer permissionIssue) {
        this.permissionIssue = permissionIssue;
    }

    public Integer getTempClosed() {
        return tempClosed;
    }

    public void setTempClosed(Integer tempClosed) {
        this.tempClosed = tempClosed;
    }

    public Integer getPermClosed() {
        return permClosed;
    }

    public void setPermClosed(Integer permClosed) {
        this.permClosed = permClosed;
    }

    public Integer getSpaceIssue() {
        return spaceIssue;
    }

    public void setSpaceIssue(Integer spaceIssue) {
        this.spaceIssue = spaceIssue;
    }

    public Integer getPaymentIssue() {
        return paymentIssue;
    }

    public void setPaymentIssue(Integer paymentIssue) {
        this.paymentIssue = paymentIssue;
    }

    public Integer getStockIssue() {
        return stockIssue;
    }

    public void setStockIssue(Integer stockIssue) {
        this.stockIssue = stockIssue;
    }

    public Integer getKitRemByRatail() {
        return kitRemByRatail;
    }

    public void setKitRemByRatail(Integer kitRemByRatail) {
        this.kitRemByRatail = kitRemByRatail;
    }

}
