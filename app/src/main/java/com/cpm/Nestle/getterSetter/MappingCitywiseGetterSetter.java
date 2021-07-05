
package com.cpm.Nestle.getterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingCitywiseGetterSetter {

    @SerializedName("Mapping_Citywise")
    @Expose
    private List<MappingCitywise> mappingCitywise = null;

    public List<MappingCitywise> getMappingCitywise() {
        return mappingCitywise;
    }

    public void setMappingCitywise(List<MappingCitywise> mappingCitywise) {
        this.mappingCitywise = mappingCitywise;
    }

}
