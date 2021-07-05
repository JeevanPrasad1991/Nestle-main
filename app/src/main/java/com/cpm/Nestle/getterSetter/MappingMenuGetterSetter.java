
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingMenuGetterSetter {

    @SerializedName("Mapping_Menu")
    @Expose
    private List<MappingMenu> mappingMenu = null;

    public List<MappingMenu> getMappingMenu() {
        return mappingMenu;
    }
}
