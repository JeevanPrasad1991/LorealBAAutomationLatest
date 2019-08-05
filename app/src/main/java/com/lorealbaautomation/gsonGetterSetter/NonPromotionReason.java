package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonPromotionReason {
    @Expose
    private Integer pReasonId;
    @Expose
    private String pReason;

    public Integer getPReasonId() {
        return pReasonId;
    }

    public void setPReasonId(Integer pReasonId) {
        this.pReasonId = pReasonId;
    }

    public String getPReason() {
        return pReason;
    }

    public void setPReason(String pReason) {
        this.pReason = pReason;
    }
}
