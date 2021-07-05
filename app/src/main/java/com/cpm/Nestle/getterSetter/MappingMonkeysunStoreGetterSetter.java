
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingMonkeysunStoreGetterSetter {

    @SerializedName("Mapping_Monkeysun")
    @Expose
    private List<MappingMonkeysun> mappingMonkeysun = null;

    public List<MappingMonkeysun> getMappingMonkeysun() {
        return mappingMonkeysun;
    }

    public void setMappingMonkeysun(List<MappingMonkeysun> mappingMonkeysun) {
        this.mappingMonkeysun = mappingMonkeysun;
    }

}
