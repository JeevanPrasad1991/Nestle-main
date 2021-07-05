package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PosCompany {
    @SerializedName("POSName")
    @Expose
    private String posCompany;

    public String getPosCompany() {
        return posCompany;
    }

    public void setPosCompany(String posCompany) {
        this.posCompany = posCompany;
    }
}
