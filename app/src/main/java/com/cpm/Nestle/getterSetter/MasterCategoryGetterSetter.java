
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterCategoryGetterSetter {

    @SerializedName("Master_Category")
    @Expose
    private List<MasterCategory> masterCategory = null;

    public List<MasterCategory> getMasterCategory() {
        return masterCategory;
    }

    @SerializedName("Master_Competitor")
    @Expose
    private List<MasterCompetitor> masterCompetitor = null;

    public List<MasterCompetitor> getMasterCompetitor() {
        return masterCompetitor;
    }

    @SerializedName("Master_NonVisibility")
    @Expose
    private List<MasterNonVisibility> masterNonVisibility = null;

    public List<MasterNonVisibility> getMasterNonVisibility() {
        return masterNonVisibility;
    }

    @SerializedName("Master_NonPromotion")
    @Expose
    private List<MasterNonPromotion> masterNonPromotion = null;

    public List<MasterNonPromotion> getMasterNonPromotion() {
        return masterNonPromotion;
    }

    @SerializedName("Master_Display")
    @Expose
    private List<MasterDisplay> masterDisplay = null;

    public List<MasterDisplay> getMasterDisplay() {
        return masterDisplay;
    }

}
