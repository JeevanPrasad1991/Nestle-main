
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendanceReportGetterSetter {

    @SerializedName("Attendance_Report")
    @Expose
    private List<AttendanceReport> attendanceReport = null;

    public List<AttendanceReport> getAttendanceReport() {
        return attendanceReport;
    }

    public void setAttendanceReport(List<AttendanceReport> attendanceReport) {
        this.attendanceReport = attendanceReport;
    }

}
