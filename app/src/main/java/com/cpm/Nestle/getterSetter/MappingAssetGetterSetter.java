
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingAssetGetterSetter {

    @SerializedName("Mapping_Asset")
    @Expose
    private List<MappingAsset> mappingAsset = null;

    public List<MappingAsset> getMappingAsset() {
        return mappingAsset;
    }

    public void setMappingAsset(List<MappingAsset> mappingAsset) {
        this.mappingAsset = mappingAsset;
    }

}
