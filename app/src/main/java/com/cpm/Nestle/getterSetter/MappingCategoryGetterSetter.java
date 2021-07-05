
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingCategoryGetterSetter {

    @SerializedName("Mapping_Category")
    @Expose
    private List<MappingCategory> mappingCategory = null;

    public List<MappingCategory> getMappingCategory() {
        return mappingCategory;
    }

    public void setMappingCategory(List<MappingCategory> mappingCategory) {
        this.mappingCategory = mappingCategory;
    }

}
