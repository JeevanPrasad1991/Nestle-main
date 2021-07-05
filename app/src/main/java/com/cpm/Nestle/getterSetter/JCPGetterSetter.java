
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JCPGetterSetter {

    @SerializedName("Mapping_JourneyPlan")
    @Expose
    private List<MappingJourneyPlan> mappingJourneyPlan = null;

    public List<MappingJourneyPlan> getMappingJourneyPlan() {
        return mappingJourneyPlan;
    }

    @SerializedName("JourneyPlan_NonMerchandised")
    @Expose
    private List<MappingJourneyPlan> journeyPlan_deviation = null;

    public List<MappingJourneyPlan> getJourneyPlan_deviation() {
        return journeyPlan_deviation;
    }

    @SerializedName("JournyPlan_DBSR")
    @Expose
    private List<MappingJourneyPlan> journyPlan_DBSR = null;

    public List<MappingJourneyPlan> getJournyPlan_DBSR() {
        return journyPlan_DBSR;
    }

    @SerializedName("JourneyPlan_NotCovered")
    @Expose
    private List<MappingJourneyPlan> journeyPlan_deviation_nc = null;

    public List<MappingJourneyPlan> getJourneyPlan_deviation_nc() {
        return journeyPlan_deviation_nc;
    }

    @SerializedName("Master_Program")
    @Expose
    private List<MasterProgram> masterProgram = null;

    public List<MasterProgram> getMasterProgram() {
        return masterProgram;
    }

}
