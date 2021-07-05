package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingSample {

    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("ChainId")
    @Expose
    private Integer chainId;
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;
    @SerializedName("SampleId")
    @Expose
    private Integer sampleId;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public MappingSample withStateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public MappingSample withChainId(Integer chainId) {
        this.chainId = chainId;
        return this;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public MappingSample withStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
        return this;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public MappingSample withSampleId(Integer sampleId) {
        this.sampleId = sampleId;
        return this;
    }

}