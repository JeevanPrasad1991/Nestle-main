
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssetMasterGetterSetter {

    @SerializedName("Asset_Master")
    @Expose
    private List<AssetMaster> assetMaster = null;

    public List<AssetMaster> getAssetMaster() {
        return assetMaster;
    }

    public void setAssetMaster(List<AssetMaster> assetMaster) {
        this.assetMaster = assetMaster;
    }

}
