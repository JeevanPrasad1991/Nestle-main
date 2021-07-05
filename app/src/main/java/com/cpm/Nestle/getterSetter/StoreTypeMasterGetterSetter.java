
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreTypeMasterGetterSetter {

    @SerializedName("Store_Type_Master")
    @Expose
    private List<StoreTypeMaster> storeTypeMaster = null;

    public List<StoreTypeMaster> getStoreTypeMaster() {
        return storeTypeMaster;
    }

    public void setStoreTypeMaster(List<StoreTypeMaster> storeTypeMaster) {
        this.storeTypeMaster = storeTypeMaster;
    }

}
