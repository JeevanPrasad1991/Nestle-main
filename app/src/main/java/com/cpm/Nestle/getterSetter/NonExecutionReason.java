
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonExecutionReason {

    @SerializedName("Reason_Id")
    @Expose
    private Integer reasonId;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("Menu_Id")
    @Expose
    private Integer menuId;

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

}
