
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterShelfGetterSetter {

    @SerializedName("Master_Shelf")
    @Expose
    private List<MasterShelf> masterShelf = null;

    public List<MasterShelf> getMasterShelf() {
        return masterShelf;
    }

    public void setMasterShelf(List<MasterShelf> masterShelf) {
        this.masterShelf = masterShelf;
    }

}
