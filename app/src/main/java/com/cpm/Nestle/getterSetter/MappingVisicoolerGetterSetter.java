
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingVisicoolerGetterSetter {

    @SerializedName("Mapping_Visicooler")
    @Expose
    private List<MappingVisicooler> mappingVisicooler = null;

    public List<MappingVisicooler> getMappingVisicooler() {
        return mappingVisicooler;
    }

    public void setMappingVisicooler(List<MappingVisicooler> mappingVisicooler) {
        this.mappingVisicooler = mappingVisicooler;
    }

}
