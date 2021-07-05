package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PosCompanyGetterSetter {

    @SerializedName("Master_POSCompany")
    @Expose
    private List<PosCompany> posCompany = null;

    public List<PosCompany> getPosCompany() {
        return posCompany;
    }

    public void setPosCompany(List<PosCompany> posCompany) {
        this.posCompany = posCompany;
    }

}
