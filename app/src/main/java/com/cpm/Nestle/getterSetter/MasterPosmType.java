
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterPosmType {

    @SerializedName("PosmTypeId")
    @Expose
    private Integer posmTypeId;
    @SerializedName("PosmType")
    @Expose
    private String posmType;

    public Integer getPosmTypeId() {
        return posmTypeId;
    }

    public void setPosmTypeId(Integer posmTypeId) {
        this.posmTypeId = posmTypeId;
    }

    public String getPosmType() {
        return posmType;
    }

    public void setPosmType(String posmType) {
        this.posmType = posmType;
    }

}
