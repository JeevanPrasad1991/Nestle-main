
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChecklistAnswer implements Serializable {

    @SerializedName("Answer_Id")
    @Expose
    private Integer answerId;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("Checklist_Id")
    @Expose
    private Integer checklistId;
    @SerializedName("QR_Code")
    @Expose
    private boolean qRCode;

    public boolean getqRCode() {
        return qRCode;
    }

    public void setqRCode(Boolean qRCode) {
        this.qRCode = qRCode;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }
}
