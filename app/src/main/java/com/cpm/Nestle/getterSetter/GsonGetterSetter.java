
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GsonGetterSetter {

    @SerializedName("LOGIN")
    @Expose
    private List<LOGIN> lOGIN = null;

    public List<LOGIN> getLOGIN() {
        return lOGIN;
    }

    public void setLOGIN(List<LOGIN> lOGIN) {
        this.lOGIN = lOGIN;
    }

}
