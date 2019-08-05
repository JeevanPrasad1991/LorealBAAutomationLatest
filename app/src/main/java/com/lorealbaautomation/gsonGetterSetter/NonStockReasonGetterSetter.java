
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NonStockReasonGetterSetter {

    @Expose
    private List<NonStockReason> nonStockReason = null;

    public List<NonStockReason> getNonStockReason() {
        return nonStockReason;
    }

    public void setNonStockReason(List<NonStockReason> nonStockReason) {
        this.nonStockReason = nonStockReason;
    }

}
