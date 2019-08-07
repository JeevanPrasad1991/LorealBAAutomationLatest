package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingVisibility {

    @SerializedName("CounterId")
    @Expose
    private Integer counterId;
    @SerializedName("PosmId")
    @Expose
    private Integer posmId;

    public Integer getCounterId() {
        return counterId;
    }

    public void setCounterId(Integer counterId) {
        this.counterId = counterId;
    }

    public Integer getPosmId() {
        return posmId;
    }

    public void setPosmId(Integer posmId) {
        this.posmId = posmId;
    }

}