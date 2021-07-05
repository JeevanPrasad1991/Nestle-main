
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuMasterGetterSetter {

    @SerializedName("Menu_Master")
    @Expose
    private List<MenuMaster> menuMaster = null;

    public List<MenuMaster> getMenuMaster() {
        return menuMaster;
    }

    public void setMenuMaster(List<MenuMaster> menuMaster) {
        this.menuMaster = menuMaster;
    }

}
