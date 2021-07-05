
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingMenu {

    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("MenuId")
    @Expose
    private Integer menuId;

    @SerializedName("StoretypeId")
    @Expose
    private Integer storetypeId;

    public Integer getStoretypeId() {
        return storetypeId;
    }

    public void setStoretypeId(Integer storetypeId) {
        this.storetypeId = storetypeId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

}
