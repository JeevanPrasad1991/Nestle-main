
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterBrandGetterSetter {

    @SerializedName("Master_Brand")
    @Expose
    private List<MasterBrand> masterBrand = null;

    public List<MasterBrand> getMasterBrand() {
        return masterBrand;
    }

    @SerializedName("Master_Company")
    @Expose
    private List<MasterCompany> masterCompany = null;

    public List<MasterCompany> getMasterCompany() {
        return masterCompany;
    }

}
