
package com.cpm.Nestle.visitor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitorDetail {

    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("EmployeeName")
    @Expose
    private String name;
    @SerializedName("DesignationName")
    @Expose
    private String designation;

    public String getLegacyCode() {
        return LegacyCode;
    }

    public void setLegacyCode(String legacyCode) {
        LegacyCode = legacyCode;
    }

    @SerializedName("LegacyCode")
    @Expose
    private String LegacyCode;

    String In_time_img,Out_time_img,Emp_code,Visit_date,In_time,Out_time,Upload_status;

    public String getIn_time_img() {
        return In_time_img;
    }

    public void setIn_time_img(String in_time_img) {
        In_time_img = in_time_img;
    }

    public String getOut_time_img() {
        return Out_time_img;
    }

    public void setOut_time_img(String out_time_img) {
        Out_time_img = out_time_img;
    }

    public String getEmp_code() {
        return Emp_code;
    }

    public void setEmp_code(String emp_code) {
        Emp_code = emp_code;
    }

    public String getVisit_date() {
        return Visit_date;
    }

    public void setVisit_date(String visit_date) {
        Visit_date = visit_date;
    }

    public String getIn_time() {
        return In_time;
    }

    public void setIn_time(String in_time) {
        In_time = in_time;
    }

    public String getOut_time() {
        return Out_time;
    }

    public void setOut_time(String out_time) {
        Out_time = out_time;
    }

    public String getUpload_status() {
        return Upload_status;
    }

    public void setUpload_status(String upload_status) {
        Upload_status = upload_status;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


}
