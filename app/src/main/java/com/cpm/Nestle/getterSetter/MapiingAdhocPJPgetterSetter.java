
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapiingAdhocPJPgetterSetter {

    @SerializedName("Mapping_AdhocJourneyPlan")
    @Expose
    private List<MappingAdhocJourneyPlan> mappingAdhocJourneyPlan = null;

    public List<MappingAdhocJourneyPlan> getMappingAdhocJourneyPlan() {
        return mappingAdhocJourneyPlan;
    }

    public void setMappingAdhocJourneyPlan(List<MappingAdhocJourneyPlan> mappingAdhocJourneyPlan) {
        this.mappingAdhocJourneyPlan = mappingAdhocJourneyPlan;
    }

}
