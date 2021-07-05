
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonPosmReasonGetterSetter {

    @SerializedName("Non_Posm_Reason")
    @Expose
    private List<NonPosmReason> nonPosmReason = null;

    public List<NonPosmReason> getNonPosmReason() {
        return nonPosmReason;
    }

    public void setNonPosmReason(List<NonPosmReason> nonPosmReason) {
        this.nonPosmReason = nonPosmReason;
    }

}
