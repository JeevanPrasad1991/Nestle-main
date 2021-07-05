
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreTypeMaster {

    @SerializedName("Store_Type_Id")
    @Expose
    private Integer storeTypeId;
    @SerializedName("Store_Type")
    @Expose
    private String storeType;
    @SerializedName("Show_Reference_Image")
    @Expose
    private Boolean showReferenceImage;

    public Boolean getShowReferenceImage() {
        return showReferenceImage;
    }

    public void setShowReferenceImage(Boolean showReferenceImage) {
        this.showReferenceImage = showReferenceImage;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

}
