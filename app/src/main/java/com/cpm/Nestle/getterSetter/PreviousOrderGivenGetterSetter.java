
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviousOrderGivenGetterSetter {

    @SerializedName("Previous_OrderGiven")
    @Expose
    private List<PreviousOrderGiven> previousOrderGiven = null;

    public List<PreviousOrderGiven> getPreviousOrderGiven() {
        return previousOrderGiven;
    }

    public void setPreviousOrderGiven(List<PreviousOrderGiven> previousOrderGiven) {
        this.previousOrderGiven = previousOrderGiven;
    }

}
