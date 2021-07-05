
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingWindowChecklistGetterSetter {

    @SerializedName("Mapping_WindowChecklist")
    @Expose
    private List<MappingWindowChecklist> mappingWindowChecklist = null;

    public List<MappingWindowChecklist> getMappingWindowChecklist() {
        return mappingWindowChecklist;
    }

    public void setMappingWindowChecklist(List<MappingWindowChecklist> mappingWindowChecklist) {
        this.mappingWindowChecklist = mappingWindowChecklist;
    }

}
