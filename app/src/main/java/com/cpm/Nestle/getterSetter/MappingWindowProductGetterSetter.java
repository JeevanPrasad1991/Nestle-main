
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingWindowProductGetterSetter {

    @SerializedName("Mapping_WindowProduct")
    @Expose
    private List<MappingWindowProduct> mappingWindowProduct = null;

    public List<MappingWindowProduct> getMappingWindowProduct() {
        return mappingWindowProduct;
    }

    public void setMappingWindowProduct(List<MappingWindowProduct> mappingWindowProduct) {
        this.mappingWindowProduct = mappingWindowProduct;
    }

}
