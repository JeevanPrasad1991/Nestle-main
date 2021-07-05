
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterWindow {

    @SerializedName("WindowId")
    @Expose
    private Integer windowId;
    @SerializedName("WindowName")
    @Expose
    private String windowName;
    @SerializedName("WindowImage")
    @Expose
    private String windowImage;

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public String getWindowImage() {
        return windowImage;
    }

    public void setWindowImage(String windowImage) {
        this.windowImage = windowImage;
    }

}
