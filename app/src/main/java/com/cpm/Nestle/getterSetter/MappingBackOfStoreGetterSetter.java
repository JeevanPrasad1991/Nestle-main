
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingBackOfStoreGetterSetter {

    @SerializedName("mapping_Back_Of_Store")
    @Expose
    private List<MappingBackOfStore> mappingBackOfStore = null;

    public List<MappingBackOfStore> getMappingBackOfStore() {
        return mappingBackOfStore;
    }

    public void setMappingBackOfStore(List<MappingBackOfStore> mappingBackOfStore) {
        this.mappingBackOfStore = mappingBackOfStore;
    }

}
