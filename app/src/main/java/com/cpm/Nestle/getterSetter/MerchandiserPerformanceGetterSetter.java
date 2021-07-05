
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MerchandiserPerformanceGetterSetter {

    @SerializedName("Merchandiser_Performance")
    @Expose
    private List<MerchandiserPerformance> merchandiserPerformance = null;

    public List<MerchandiserPerformance> getMerchandiserPerformance() {
        return merchandiserPerformance;
    }

    public void setMerchandiserPerformance(List<MerchandiserPerformance> merchandiserPerformance) {
        this.merchandiserPerformance = merchandiserPerformance;
    }

}
