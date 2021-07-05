
package com.cpm.Nestle.getterSetter;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingSecondaryVisibilityGetterSetter implements Serializable {

    @SerializedName("Mapping_Secondary_Visibility")
    @Expose
    private List<MappingSecondaryVisibility> mappingSecondaryVisibility = null;

    public List<MappingSecondaryVisibility> getMappingSecondaryVisibility() {
        return mappingSecondaryVisibility;
    }

    public void setMappingSecondaryVisibility(List<MappingSecondaryVisibility> mappingSecondaryVisibility) {
        this.mappingSecondaryVisibility = mappingSecondaryVisibility;
    }

}
