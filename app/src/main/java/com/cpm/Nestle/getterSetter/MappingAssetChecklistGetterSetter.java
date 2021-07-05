
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingAssetChecklistGetterSetter {

    @SerializedName("Mapping_AssetChecklist")
    @Expose
    private List<MappingAssetChecklist> mappingAssetChecklist = null;

    public List<MappingAssetChecklist> getMappingAssetChecklist() {
        return mappingAssetChecklist;
    }

    public void setMappingAssetChecklist(List<MappingAssetChecklist> mappingAssetChecklist) {
        this.mappingAssetChecklist = mappingAssetChecklist;
    }

}
