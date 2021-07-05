
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterProductFlavourGetterSetter {

    @SerializedName("Master_ProductFlavour")
    @Expose
    private List<MasterProductFlavour> masterProductFlavour = null;

    public List<MasterProductFlavour> getMasterProductFlavour() {
        return masterProductFlavour;
    }

    public void setMasterProductFlavour(List<MasterProductFlavour> masterProductFlavour) {
        this.masterProductFlavour = masterProductFlavour;
    }

}
