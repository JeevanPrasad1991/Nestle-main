
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingMenuChecklistGetterSetter {

    @SerializedName("Mapping_Menu_Checklist")
    @Expose
    private List<MappingMenuChecklist> mappingMenuChecklist = null;

    public List<MappingMenuChecklist> getMappingMenuChecklist() {
        return mappingMenuChecklist;
    }

    public void setMappingMenuChecklist(List<MappingMenuChecklist> mappingMenuChecklist) {
        this.mappingMenuChecklist = mappingMenuChecklist;
    }

}
