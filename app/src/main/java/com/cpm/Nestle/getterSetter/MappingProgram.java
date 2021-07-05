package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingProgram {
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("SubProgramId")
    @Expose
    private Integer subProgramId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getSubProgramId() {
        return subProgramId;
    }

    public void setSubProgramId(Integer subProgramId) {
        this.subProgramId = subProgramId;
    }

}
