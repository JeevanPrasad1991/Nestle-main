
package com.cpm.Nestle.getterSetter;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterLocationTypeGetter implements Serializable
{

    @SerializedName("Master_LocationType")
    @Expose
    private List<MasterLocationType> masterLocationType = null;
    private final static long serialVersionUID = 776443119930573440L;

    public List<MasterLocationType> getMasterLocationType() {
        return masterLocationType;
    }

    public void setMasterLocationType(List<MasterLocationType> masterLocationType) {
        this.masterLocationType = masterLocationType;
    }

}
