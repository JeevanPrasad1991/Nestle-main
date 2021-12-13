
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterChecklistGetterSetter {

    @SerializedName("Master_Checklist")
    @Expose
    private List<MasterChecklist> masterChecklist = null;

    public List<MasterChecklist> getMasterChecklist() {
        return masterChecklist;
    }

    @SerializedName("Master_PromotionChecklist")
    @Expose
    private List<MasterPromotionCheck> masterPromotionChecklist = null;

    public List<MasterPromotionCheck> getMasterPromotionChecklist() {
        return masterPromotionChecklist;
    }
    @SerializedName("Master_PromotionChecklistReason")
    @Expose
    private List<MasterPromotionChecklistReason> masterPromotionChecklistReason = null;

    public List<MasterPromotionChecklistReason> getMasterPromotionChecklistReason() {
        return masterPromotionChecklistReason;
    }


}
