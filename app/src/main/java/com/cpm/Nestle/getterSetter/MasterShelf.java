
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterShelf {

    @SerializedName("Shelf")
    @Expose
    private Integer shelf;

    public Integer getShelf() {
        return shelf;
    }

    public void setShelf(Integer shelf) {
        this.shelf = shelf;
    }

}
