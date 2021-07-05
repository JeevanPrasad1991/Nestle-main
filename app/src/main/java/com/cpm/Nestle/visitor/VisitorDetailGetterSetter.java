
package com.cpm.Nestle.visitor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VisitorDetailGetterSetter {

    @SerializedName("Visitor_Search")
    @Expose
    private List<VisitorDetail> visitorDetail = null;

    public List<VisitorDetail> getVisitorDetail() {
        return visitorDetail;
    }

    public void setVisitorDetail(List<VisitorDetail> visitorDetail) {
        this.visitorDetail = visitorDetail;
    }

}
