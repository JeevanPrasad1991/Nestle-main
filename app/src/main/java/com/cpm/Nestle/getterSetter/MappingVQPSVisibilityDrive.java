
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingVQPSVisibilityDrive {

    @SerializedName("PosmId")
    @Expose
    private Integer posmId;

    public Integer getPosmId() {
        return posmId;
    }

    public void setPosmId(Integer posmId) {
        this.posmId = posmId;
    }

    public String getPosmName() {
        return posmName;
    }

    public void setPosmName(String posmName) {
        this.posmName = posmName;
    }

    String posmName;

}
