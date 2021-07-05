
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingShareOfShelfGetterSetter {

    @SerializedName("mapping_Share_Of_Shelf")
    @Expose
    private List<MappingShareOfShelf> mappingShareOfShelf = null;

    public List<MappingShareOfShelf> getMappingShareOfShelf() {
        return mappingShareOfShelf;
    }

    public void setMappingShareOfShelf(List<MappingShareOfShelf> mappingShareOfShelf) {
        this.mappingShareOfShelf = mappingShareOfShelf;
    }

}
