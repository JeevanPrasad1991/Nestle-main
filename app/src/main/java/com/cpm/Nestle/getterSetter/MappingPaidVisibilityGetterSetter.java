
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingPaidVisibilityGetterSetter {

    @SerializedName("Mapping_Paid_Visibility")
    @Expose
    private List<MappingPaidVisibility> mappingPaidVisibility = null;

    public List<MappingPaidVisibility> getMappingPaidVisibility() {
        return mappingPaidVisibility;
    }

    public void setMappingPaidVisibility(List<MappingPaidVisibility> mappingPaidVisibility) {
        this.mappingPaidVisibility = mappingPaidVisibility;
    }

}
