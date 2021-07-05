
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonExecutionReasonGetterSetter {

    @SerializedName("Non_Execution_Reason")
    @Expose
    private List<NonExecutionReason> nonExecutionReason = null;

    public List<NonExecutionReason> getNonExecutionReason() {
        return nonExecutionReason;
    }

    public void setNonExecutionReason(List<NonExecutionReason> nonExecutionReason) {
        this.nonExecutionReason = nonExecutionReason;
    }

}
