package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingCounterGroupBrand {
    @SerializedName("CounterGroupId")
    @Expose
    private Integer counterGroupId;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;

    public Integer getCounterGroupId() {
        return counterGroupId;
    }

    public void setCounterGroupId(Integer counterGroupId) {
        this.counterGroupId = counterGroupId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

}