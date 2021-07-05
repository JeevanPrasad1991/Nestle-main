
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingProductAssortmentGetterSetter {

    @SerializedName("Mapping_ProductAssortment")
    @Expose
    private List<MappingProductAssortment> mappingProductAssortment = null;

    public List<MappingProductAssortment> getMappingProductAssortment() {
        return mappingProductAssortment;
    }

    public void setMappingProductAssortment(List<MappingProductAssortment> mappingProductAssortment) {
        this.mappingProductAssortment = mappingProductAssortment;
    }

}
