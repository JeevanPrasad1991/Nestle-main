
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonAssetReasonGetterSetter {

    @SerializedName("Non_Asset_Reason")
    @Expose
    private List<NonAssetReason> nonAssetReason = null;

    public List<NonAssetReason> getNonAssetReason() {
        return nonAssetReason;
    }

    public void setNonAssetReason(List<NonAssetReason> nonAssetReason) {
        this.nonAssetReason = nonAssetReason;
    }

}
