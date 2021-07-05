
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuMaster implements Serializable {

    @SerializedName("MenuId")
    @Expose
    private Integer menuId;
    @SerializedName("MenuName")
    @Expose
    private String menuName;
    @SerializedName("NormalIcon")
    @Expose
    private String normalIcon;
    @SerializedName("TickIcon")
    @Expose
    private String tickIcon;
    @SerializedName("GreyIcon")
    @Expose
    private String greyIcon;
    @SerializedName("MenuPath")
    @Expose
    private String menuPath;
    @SerializedName("BGColor")
    @Expose
    private String bGColor;
    @SerializedName("MenuSequence")
    @Expose
    private Integer menuSequence;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getNormalIcon() {
        return normalIcon;
    }

    public void setNormalIcon(String normalIcon) {
        this.normalIcon = normalIcon;
    }

    public String getTickIcon() {
        return tickIcon;
    }

    public void setTickIcon(String tickIcon) {
        this.tickIcon = tickIcon;
    }

    public String getGreyIcon() {
        return greyIcon;
    }

    public void setGreyIcon(String greyIcon) {
        this.greyIcon = greyIcon;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getBGColor() {
        return bGColor;
    }

    public void setBGColor(String bGColor) {
        this.bGColor = bGColor;
    }

    public Integer getMenuSequence() {
        return menuSequence;
    }

    public void setMenuSequence(Integer menuSequence) {
        this.menuSequence = menuSequence;
    }

}
