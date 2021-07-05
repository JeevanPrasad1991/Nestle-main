
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingSamplingGetterSetter {

    @SerializedName("Mapping_Sampling")
    @Expose
    private List<MappingSampling> mappingSampling = null;

    public List<MappingSampling> getMappingSampling() {
        return mappingSampling;
    }

    public void setMappingSampling(List<MappingSampling> mappingSampling) {
        this.mappingSampling = mappingSampling;
    }

}
