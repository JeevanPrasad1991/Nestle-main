
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JournyPlanDBSRGetterSetter {

    @SerializedName("JournyPlan_DBSR")
    @Expose
    private List<JournyPlanDBSR> journyPlanDBSR = null;

    public List<JournyPlanDBSR> getJournyPlanDBSR() {
        return journyPlanDBSR;
    }

    public void setJournyPlanDBSR(List<JournyPlanDBSR> journyPlanDBSR) {
        this.journyPlanDBSR = journyPlanDBSR;
    }

}
