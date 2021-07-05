
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingCTUGetterSetter {

    @SerializedName("Mapping_CTU")
    @Expose
    private List<MappingCTU> mappingCTU = null;

    public List<MappingCTU> getMappingCTU() {
        return mappingCTU;
    }

    public void setMappingCTU(List<MappingCTU> mappingCTU) {
        this.mappingCTU = mappingCTU;
    }

}
