
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterWindowGetterSetter {

    @SerializedName("Master_Window")
    @Expose
    private List<MasterWindow> masterWindow = null;

    public List<MasterWindow> getMasterWindow() {
        return masterWindow;
    }

    public void setMasterWindow(List<MasterWindow> masterWindow) {
        this.masterWindow = masterWindow;
    }

}
