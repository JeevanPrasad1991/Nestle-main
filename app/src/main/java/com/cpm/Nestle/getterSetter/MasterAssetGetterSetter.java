
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterAssetGetterSetter {
    @SerializedName("Master_Asset")
    @Expose
    private List<MasterAsset> masterAsset = null;

    public List<MasterAsset> getMasterAsset() {
        return masterAsset;
    }

    @SerializedName("Mapping_PaidVisibility")
    @Expose
    private List<MappingPaidVisibility> mappingPaidVisibility = null;

    public List<MappingPaidVisibility> getMappingPaidVisibility() {
        return mappingPaidVisibility;
    }
    @SerializedName("Master_NonAssetReason")
    @Expose
    private List<MasterNonAssetReason> masterNonAssetReason = null;

    public List<MasterNonAssetReason> getMasterNonAssetReason() {
        return masterNonAssetReason;
    }

    @SerializedName("Master_AssetLocation")
    @Expose
    private List<MasterAssetLocation> masterAssetLocation = null;

    public List<MasterAssetLocation> getMasterAssetLocation() {
        return masterAssetLocation;
    }

}
